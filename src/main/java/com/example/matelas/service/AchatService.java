package com.example.matelas.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.matelas.entity.Achat;
import com.example.matelas.entity.Bloc;
import com.example.matelas.entity.Produit;

import com.example.matelas.repository.AchatRepository;
import com.example.matelas.repository.SortieProduitRepository;

@Service
public class AchatService {

    @Autowired
    private AchatRepository achatRepository;

    @Autowired
    private SortieProduitService sortieProduitService;

    public List<Achat> getAchatDisponible(LocalDateTime date, Produit produit) {

        List<Achat> achats = achatRepository.findAchatUtiliseByDate(date, produit.getId());

        for (int i = 0; i < achats.size(); i++) {

            double reste = achats.get(i).getQuantite()
                    - sortieProduitService.getAllSortie(achats.get(i));
            achats.get(i).setReste(reste);
        }
        return achats;
    }

    public void updateAchat(Achat a) {
        achatRepository.saveAndFlush(a);
    }
}
