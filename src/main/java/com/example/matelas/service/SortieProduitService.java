package com.example.matelas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.matelas.entity.Achat;
import com.example.matelas.entity.SortieProduit;
import com.example.matelas.repository.SortieProduitRepository;

@Service
public class SortieProduitService {
    @Autowired
    private SortieProduitRepository sortieProduitRepository;

    public void insertSortie(SortieProduit sortie) {
        sortieProduitRepository.save(sortie);
    }

    public double getAllSortie(Achat a) {
        double total = 0;
        Double s = sortieProduitRepository.findTotalSortieAchat(a.getId());
        if (s != null) {
            total = s;
        }
        return total;
    }

}
