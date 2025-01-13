package com.example.matelas.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.matelas.entity.FormeUsuelle;
import com.example.matelas.repository.FormeUsuelleRepository;
import com.example.matelas.service.FormeUsuelleService;

public class EtatStockFormeUsuelle {

    private FormeUsuelle formeUsuelle;
    private int quantite;
    private double prixDeRevient;
    private double prixDeVente;

    public FormeUsuelle getFormeUsuelle() {
        return formeUsuelle;
    }

    public void setFormeUsuelle(FormeUsuelle formeUsuelle) {
        this.formeUsuelle = formeUsuelle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixDeRevient() {
        return prixDeRevient;
    }

    public void setPrixDeRevient(double prixDeRevient) {
        this.prixDeRevient = prixDeRevient;
    }

    public double getPrixDeVente() {
        return prixDeVente;
    }

    public void setPrixDeVente(double priveDeVente) {
        this.prixDeVente = priveDeVente;
    }

}
