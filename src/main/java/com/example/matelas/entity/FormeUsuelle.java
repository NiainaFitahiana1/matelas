package com.example.matelas.entity;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.matelas.model.EtatStockFormeUsuelle;
import com.example.matelas.repository.FormeUsuelleRepository;

import jakarta.persistence.*;

@Entity
public class FormeUsuelle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private Double largeur;
    private Double longueur;
    private Double hauteur;
    private Double prixVente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getLargeur() {
        return largeur;
    }

    public void setLargeur(Double largeur) {
        this.largeur = largeur;
    }

    public Double getLongueur() {
        return longueur;
    }

    public void setLongueur(Double longueur) {
        this.longueur = longueur;
    }

    public Double getHauteur() {
        return hauteur;
    }

    public void setHauteur(Double hauteur) {
        this.hauteur = hauteur;
    }

    public Double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public double volume() {
        return longueur * largeur * hauteur;
    }

}
