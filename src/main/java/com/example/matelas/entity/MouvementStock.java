package com.example.matelas.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class MouvementStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "forme_usuelle_id", referencedColumnName = "id")
    private FormeUsuelle formeUsuelle;

    private Integer entree;
    private Integer sortie;
    private Double prixRevient;

    @Column(name = "date_mouvement")
    private LocalDateTime dateMouvement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormeUsuelle getFormeUsuelle() {
        return formeUsuelle;
    }

    public void setFormeUsuelle(FormeUsuelle formeUsuelle) {
        this.formeUsuelle = formeUsuelle;
    }

    public Integer getEntree() {
        return entree;
    }

    public void setEntree(Integer entree) {
        this.entree = entree;
    }

    public Integer getSortie() {
        return sortie;
    }

    public void setSortie(Integer sortie) {
        this.sortie = sortie;
    }

    public Double getPrixRevient() {
        return prixRevient;
    }

    public void setPrixRevient(Double prixRevient) {
        this.prixRevient = prixRevient;
    }

    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }
}
