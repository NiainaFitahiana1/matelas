package com.example.matelas.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Transformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_transformation")
    private LocalDateTime dateTransformation;

    @ManyToOne
    @JoinColumn(name = "bloc_id", referencedColumnName = "id")
    private Bloc bloc;

    private Double tetaEcart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTransformation() {
        return dateTransformation;
    }

    public void setDateTransformation(LocalDateTime dateTransformation) {
        this.dateTransformation = dateTransformation;
    }

    public Bloc getBloc() {
        return bloc;
    }

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }

    public Double getTetaEcart() {
        return tetaEcart;
    }

    public void setTetaEcart(Double tetaEcart) {
        this.tetaEcart = tetaEcart;
    }
}
