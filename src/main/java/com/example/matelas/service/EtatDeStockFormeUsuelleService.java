package com.example.matelas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.matelas.entity.FormeUsuelle;
import com.example.matelas.model.EtatStockFormeUsuelle;
import com.example.matelas.repository.FormeUsuelleRepository;

@Service
public class EtatDeStockFormeUsuelleService {
    @Autowired
    private FormeUsuelleRepository formeUsuelleRepository;
    @Autowired
    private FormeUsuelleService formeUsuelleService;

    public List<EtatStockFormeUsuelle> getAllEtatDeStockFormeUsuells() {
        List<FormeUsuelle> formeUsuelles = formeUsuelleRepository.findAll();
        List<EtatStockFormeUsuelle> etatStockFormeUsuelles = new ArrayList<>();
        for (int i = 0; i < formeUsuelles.size(); i++) {
            EtatStockFormeUsuelle etat = formeUsuelleService.getEtatDeStock(formeUsuelles.get(i));
            etatStockFormeUsuelles.add(etat);
        }
        return etatStockFormeUsuelles;
    }
}
