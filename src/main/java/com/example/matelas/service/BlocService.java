package com.example.matelas.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.matelas.entity.Achat;
import com.example.matelas.entity.Bloc;
import com.example.matelas.entity.DetailBloc;
import com.example.matelas.entity.DetailTransformation;
import com.example.matelas.entity.FormeUsuelle;
import com.example.matelas.entity.FormuleFabrication;
import com.example.matelas.entity.Machine;
import com.example.matelas.entity.Produit;
import com.example.matelas.entity.SortieProduit;

import com.example.matelas.model.EtatStockBlocMaxBenef;
import com.example.matelas.model.EtatStockMinPerte;
import com.example.matelas.model.FormuleBloc;

import com.example.matelas.repository.BlocRepository;
import com.example.matelas.repository.DetailBlocRepository;
import com.example.matelas.repository.DetailTransformationRepository;
import com.example.matelas.repository.FormeUsuelleRepository;
import com.example.matelas.repository.FormuleFabricationRepository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlocService {
    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private DetailBlocRepository detailBlocRepository;

    @Autowired
    private FormeUsuelleRepository formeUsuelleRepository;
    @Autowired
    private DetailTransformationRepository detailTransformationRepository;

    @Autowired
    private FormuleFabricationRepository formuleFabricationRepository;

    @Autowired
    private AchatService achatService;

    @Autowired
    private SortieProduitService sortieProduitService;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BlocService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String generateNameForBloc() {
        Long nextSequenceValue = jdbcTemplate.queryForObject("SELECT nextval('seq_bloc_name')", Long.class);
        return "BL" + nextSequenceValue;
    }

    public Bloc saveBloc(Bloc bloc) {
        if (bloc.getNom() == null || bloc.getNom() == "") {
            bloc.setNom(generateNameForBloc());
        }
        return blocRepository.save(bloc);
    }

    public void saveAllBloc(List<Bloc> blocs) {
        blocRepository.saveAll(blocs);
    }

    public List<Bloc> getAllBlocDisponible() {
        return blocRepository.getAllBlocDisponible();
    }

    public FormeUsuelle getFormeUsuelleWithMaxBenef() {
        return formeUsuelleRepository.findFormeUsuelleWithBestPriceToVolumeRatio();
    }

    public FormeUsuelle getFormeUsuelleWithMinVolume() {
        return formeUsuelleRepository.findDFormeUsuelleWithMinVolume();
    }

    public EtatStockBlocMaxBenef getEtatStockBlocMaxBenef(Bloc bloc) {
        EtatStockBlocMaxBenef etatStockBlocMaxBenef = new EtatStockBlocMaxBenef();
        etatStockBlocMaxBenef.setBloc(bloc);
        FormeUsuelle formeUsuelle = getFormeUsuelleWithMaxBenef();
        int quantitePossible = getQuantiteFormeUsuellePossible(bloc, formeUsuelle);
        etatStockBlocMaxBenef.setFormeUsuelle(formeUsuelle);
        etatStockBlocMaxBenef.setPrixDeVente(quantitePossible * formeUsuelle.getPrixVente());
        double prixDeVente = quantitePossible * formeUsuelle.getPrixVente();
        DecimalFormat df = new DecimalFormat("#,###.00");
        System.out.println("Prix de vente " + df.format(prixDeVente));
        etatStockBlocMaxBenef.setQuantitePossible(quantitePossible);
        return etatStockBlocMaxBenef;
    }

    public EtatStockMinPerte getEtatStockBlocMinPerte(Bloc bloc) {
        EtatStockMinPerte etatStockMinPerte = new EtatStockMinPerte();
        etatStockMinPerte.setBloc(bloc);
        FormeUsuelle formeUsuelle = getFormeUsuelleWithMinVolume();
        int quantitePossible = getQuantiteFormeUsuellePossible(bloc, formeUsuelle);
        etatStockMinPerte.setFormeUsuelle(formeUsuelle);
        etatStockMinPerte.setPrixDeVente(quantitePossible * formeUsuelle.getPrixVente());
        etatStockMinPerte.setQuantitePossible(quantitePossible);
        return etatStockMinPerte;
    }

    public List<Bloc> getAll() {
        return blocRepository.findAll();
    }

    public int getQuantiteFormeUsuellePossible(Bloc bloc, FormeUsuelle formeUsuelle) {
        int nbFormesLongueur = (int) (bloc.getLongueur() / formeUsuelle.getLongueur());
        int nbFormesLargeur = (int) (bloc.getLargeur() / formeUsuelle.getLargeur());
        int nbFormesHauteur = (int) (bloc.getHauteur() / formeUsuelle.getHauteur());

        return nbFormesLongueur * nbFormesLargeur * nbFormesHauteur;
    }

    // public int getQuantiteFormeUsuellePossible(Bloc bloc, FormeUsuelle
    // formeUsuelle) {
    // int nbFormesLongueur = (int) (bloc.getLongueur() /
    // formeUsuelle.getLongueur());
    // int nbFormesLargeur = (int) (bloc.getLargeur() / formeUsuelle.getLargeur());
    // int nbFormesHauteur = (int) (bloc.getHauteur() / formeUsuelle.getHauteur());

    // return nbFormesLongueur * nbFormesLargeur * nbFormesHauteur;
    // }

    public Bloc getBlocFille(Bloc bloc) {
        Bloc res = blocRepository.findBlocFille(bloc.getId());
        return res;
    }

    public List<Bloc> getAllBlocDescendant(Bloc bloc) {
        List<Bloc> res = new ArrayList<>();

        Bloc b = blocRepository.findBlocFille(bloc.getId());

        if (b == null) {
            return res;
        }
        res.add(b);
        while (true) {
            b = blocRepository.findBlocFille(b.getId());
            if (b == null) {
                break;

            }
            res.add(b);
        }
        return res;
    }

    public void updatePrixRevientAllBloc(Bloc bloc) {
        List<Bloc> blocs = getAllBlocDescendant(bloc);
        for (int i = 0; i < blocs.size(); i++) {
            blocs.get(i)
                    .setPrixRevient(calculatePrixDeRevientByBloc(bloc, blocs.get(i).volume()));
            blocRepository.save(blocs.get(i));
            updatePrixRevientAllDetailTransformation(blocs.get(i));
        }
    }

    public Double calculatePrixDeRevientByBloc(Bloc bloc, Double volume) {
        Double volumeBloc = bloc.getHauteur() * bloc.getLargeur() * bloc.getLongueur();
        Double prixDeRevient = (volume * bloc.getPrixRevient()) / volumeBloc;
        return prixDeRevient;
    }

    public void updatePrixRevientAllDetailTransformation(Bloc bloc) {
        List<DetailTransformation> detailTransformations = detailTransformationRepository
                .findDetailTransformationByBloc(Math.toIntExact(bloc.getId()));

        for (int i = 0; i < detailTransformations.size(); i++) {
            detailTransformations.get(i).setPrixRevient(calculatePrixDeRevientByBloc(bloc,
                    detailTransformations.get(i).getFormeUsuelle().volume()));
            detailTransformationRepository.save(detailTransformations.get(i));
        }

    }

    public FormuleBloc getFormule() {
        FormuleBloc res = new FormuleBloc();
        res.setProduits(formuleFabricationRepository.findFormuleFabrication());
        return res;
    }

    public List<Achat> setAchatProduitUtiliserByBloc(Bloc bloc, Produit produit) {

        FormuleBloc formule = getFormule();
        FormuleFabrication formuleFabrication = new FormuleFabrication();
        for (int i = 0; i < formule.getProduits().size(); i++) {
            if (formule.getProduits().get(i).getId() == produit.getId()) {
                formuleFabrication = formule.getProduits().get(i);
            }
        }
        Double quantiteProduitNecessaire = bloc.volume() * formuleFabrication.getQuantite();

        List<Achat> resultats = new ArrayList<>();
        List<Achat> achats = achatService.getAchatDisponible(bloc.getDateCreation(), produit);
        double reste = achats.get(0).getQuantite() - quantiteProduitNecessaire;
        if (reste < 0) {
            System.out.println(" Reste negative " + reste);
            double quantiteN = bloc.volume() * formuleFabrication.getQuantite();

            for (int i = 0; i < achats.size(); i++) {
                double r = achats.get(i).getReste() - quantiteN;
                if (r == 0 || r > 0) {
                    if (r == 0) {
                        achats.get(i).setIsAvailable(false);
                        achatService.updateAchat(achats.get(i));
                    }
                    SortieProduit sortie = new SortieProduit();
                    sortie.setAchat(achats.get(i));
                    sortie.setDateSortie(bloc.getDateCreation());
                    sortie.setQuantite(quantiteN);
                    sortieProduitService.insertSortie(sortie);
                    resultats.add(achats.get(i));
                    break;
                }
                achats.get(i).setIsAvailable(false);
                achatService.updateAchat(achats.get(i));
                SortieProduit sortie = new SortieProduit();
                sortie.setAchat(achats.get(i));
                sortie.setDateSortie(bloc.getDateCreation());
                sortie.setQuantite(achats.get(i).getReste());
                sortieProduitService.insertSortie(sortie);
                resultats.add(achats.get(i));
                quantiteN = quantiteN - achats.get(i).getReste();
            }

        } else {
            if (reste == 0) {
                achats.get(0).setIsAvailable(false);
                achatService.updateAchat(achats.get(0));
            }
            SortieProduit sortie = new SortieProduit();
            sortie.setAchat(achats.get(0));
            sortie.setDateSortie(bloc.getDateCreation());
            sortie.setQuantite(quantiteProduitNecessaire);
            sortieProduitService.insertSortie(sortie);
            resultats.add(achats.get(0));
        }
        return achats;
    }

    public DetailBloc saveDetailBloc(Bloc bloc, Produit produit) {
        List<Achat> achats = setAchatProduitUtiliserByBloc(bloc, produit);
        double prixUnitaire = 0;
        for (int i = 0; i < achats.size(); i++) {
            prixUnitaire = prixUnitaire + achats.get(i).getPrixUnitaire();
        }
        prixUnitaire = prixUnitaire / achats.size();

        DetailBloc detailBloc = new DetailBloc();
        detailBloc.setBloc(bloc);
        detailBloc.setProduit(produit);
        detailBloc.setPrixUnitaire(prixUnitaire);
        return detailBlocRepository.save(detailBloc);
    }

    public List<DetailBloc> getDetailBlocByBloc(Bloc bloc) {
        List<DetailBloc> detail = new ArrayList<>();
        FormuleBloc formule = getFormule();
        for (int i = 0; i < formule.getProduits().size(); i++) {
            DetailBloc d = detailBlocRepository.findDetailBloc(formule.getProduits().get(i).getProduit().getId(),
                    bloc.getId());
            if (d == null) {
                d = saveDetailBloc(bloc, formule.getProduits().get(i).getProduit());
            }
            detail.add(d);
        }
        return detail;
    }

    public double getQuantiteNecessaireProduitParMetreCube(Produit p) {
        FormuleBloc f = getFormule();
        for (int i = 0; i < f.getProduits().size(); i++) {
            if (f.getProduits().get(i).getId() == p.getId()) {
                return f.getProduits().get(i).getQuantite();
            }
        }
        return 0;
    }

    public double getPrixDeRevientTheorique(Bloc bloc) {
        List<DetailBloc> detailBlocs = getDetailBlocByBloc(bloc);
        double pr = 0;
        for (int i = 0; i < detailBlocs.size(); i++) {
            pr = pr + detailBlocs.get(i).getPrixUnitaire()
                    * getQuantiteNecessaireProduitParMetreCube(detailBlocs.get(i).getProduit());
        }
        return pr;
    }

    public double getEcartPrixDeRevientBloc(Bloc bloc) {
        Double res = bloc.getPrixRevientEnMetreCubique() - getPrixDeRevientTheorique(bloc);
        return res;
    }

    public List<Bloc> getAllBlocByMachine(Machine m) {
        return blocRepository.findBlocByMachine(m.getId());
    }

}
