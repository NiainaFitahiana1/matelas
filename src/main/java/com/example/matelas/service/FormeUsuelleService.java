package com.example.matelas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.matelas.repository.DetailTransformationRepository;
import com.example.matelas.repository.FormeUsuelleRepository;
import com.example.matelas.entity.DetailTransformation;
import com.example.matelas.entity.FormeUsuelle;
import com.example.matelas.model.EtatStockFormeUsuelle;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormeUsuelleService {

    @Autowired
    private DetailTransformationRepository detailTransformationRepository;

    @Autowired
    private FormeUsuelleRepository formeUsuelleRepository;

    public List<FormeUsuelle> getAllFormeUsuelle() {
        return formeUsuelleRepository.findAll();
    }

    public EtatStockFormeUsuelle getEtatDeStock(FormeUsuelle formeUsuelle) {
        EtatStockFormeUsuelle etatStockFormeUsuelle = new EtatStockFormeUsuelle();
        etatStockFormeUsuelle.setFormeUsuelle(formeUsuelle);
        etatStockFormeUsuelle.setPrixDeRevient(getPrixDeRevientDeStockFormeUsuelle(formeUsuelle));
        int quantite = getNombreDeStockFormeUsuelle(formeUsuelle);
        etatStockFormeUsuelle.setQuantite(quantite);
        etatStockFormeUsuelle.setPrixDeVente(quantite * formeUsuelle.getPrixVente());
        return etatStockFormeUsuelle;
    }

    public int getNombreDeStockFormeUsuelle(FormeUsuelle formeUsuelle) {
        List<DetailTransformation> detailTransformations = detailTransformationRepository
                .findDetailTransformationByIdFormeUsuelle(formeUsuelle.getId());
        int quantite = 0;
        for (int i = 0; i < detailTransformations.size(); i++) {
            quantite = quantite + detailTransformations.get(i).getQuantite();
        }
        return quantite;
    }

    public double getPrixDeRevientDeStockFormeUsuelle(FormeUsuelle formeUsuelle) {
        List<DetailTransformation> detailTransformations = detailTransformationRepository
                .findDetailTransformationByIdFormeUsuelle(formeUsuelle.getId());
        double prixDeRevient = 0;
        for (int i = 0; i < detailTransformations.size(); i++) {
            prixDeRevient = prixDeRevient
                    + (detailTransformations.get(i).getPrixRevient() * detailTransformations.get(i).getQuantite());
        }
        return prixDeRevient;
    }

}
