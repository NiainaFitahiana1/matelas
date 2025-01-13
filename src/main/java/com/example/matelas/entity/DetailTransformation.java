package com.example.matelas.entity;

import jakarta.persistence.*;

@Entity
public class DetailTransformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transformation_id", referencedColumnName = "id")
    private Transformation transformation;

    @ManyToOne
    @JoinColumn(name = "forme_usuelle_id", referencedColumnName = "id")
    private FormeUsuelle formeUsuelle;

    private Integer quantite;

    private Double prixRevient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    public FormeUsuelle getFormeUsuelle() {
        return formeUsuelle;
    }

    public void setFormeUsuelle(FormeUsuelle formeUsuelle) {
        this.formeUsuelle = formeUsuelle;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPrixRevient() {
        return prixRevient;
    }

    public void setPrixRevient(Double prixRevient) {
        this.prixRevient = prixRevient;
    }
}
