package com.example.matelas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.matelas.entity.Bloc;
import com.example.matelas.entity.DetailTransformation;
import com.example.matelas.entity.Transformation;
import com.example.matelas.repository.DetailTransformationRepository;
import com.example.matelas.repository.TransformationRepository;

@Service
public class TransformationService {

    @Autowired
    private BlocService blocService;

    @Autowired
    private TransformationRepository transfomationRepository;

    @Autowired
    private DetailTransformationRepository detailTransformationRepository;

    public void saveTransformation(Transformation transformation, List<DetailTransformation> detailTransformations,
            Double largeurRestante, Double longueurRestante,
            Double hauteurRestante) {

        try {
            Transformation transform = transfomationRepository.save(transformation);
            for (int i = 0; i < detailTransformations.size(); i++) {
                Double volumeDetail = detailTransformations.get(i).getFormeUsuelle().getLargeur()
                        * detailTransformations.get(i).getFormeUsuelle().getLongueur()
                        * detailTransformations.get(i).getFormeUsuelle().getHauteur();
                detailTransformations.get(i).setTransformation(transform);
                detailTransformations.get(i)
                        .setPrixRevient(calculatePrixDeRevientByBloc(transform.getBloc(), volumeDetail));
            }
            saveDetailTransformation(detailTransformations);
            Bloc newBloc = new Bloc();
            newBloc.setLargeur(largeurRestante);
            newBloc.setLongueur(longueurRestante);
            newBloc.setHauteur(hauteurRestante);
            newBloc.setSource(transform.getBloc());
            newBloc.setDateCreation(transformation.getDateTransformation());
            Double volume = largeurRestante * longueurRestante * hauteurRestante;
            newBloc.setPrixRevient(calculatePrixDeRevientByBloc(transform.getBloc(), volume));
            blocService.saveBloc(newBloc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Double calculatePrixDeRevientByBloc(Bloc bloc, Double volume) {
        Double volumeBloc = bloc.getHauteur() * bloc.getLargeur() * bloc.getLongueur();
        Double prixDeRevient = (volume * bloc.getPrixRevient()) / volumeBloc;
        return prixDeRevient;
    }

    public void saveDetailTransformation(List<DetailTransformation> detailTransformation) {
        for (int i = 0; i < detailTransformation.size(); i++) {
            detailTransformationRepository.save(detailTransformation.get(i));
        }
    }

    public double calculateTetaEcart(Bloc initial, List<DetailTransformation> details, double longueur, double largeur,
            double hauteur) {
        double volumeBlocInitial = initial.getHauteur() * initial.getLargeur() * initial.getLongueur();
        double volumeBlocRestante = longueur * largeur * hauteur;
        double volumeFormeUsuelles = 0;
        for (int i = 0; i < details.size(); i++) {
            volumeFormeUsuelles = volumeFormeUsuelles + ((details.get(i).getFormeUsuelle().getHauteur()
                    * details.get(i).getFormeUsuelle().getLargeur() * details.get(i).getFormeUsuelle().getLongueur())
                    * details.get(i).getQuantite());
        }

        double ecart = ((volumeBlocInitial) - (volumeBlocRestante + volumeFormeUsuelles)) * 100 / volumeBlocInitial;

        return ecart;
    }

}