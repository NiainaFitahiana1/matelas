package com.example.matelas.model;

import com.example.matelas.entity.Bloc;
import com.example.matelas.entity.FormeUsuelle;

public class EtatStockBlocMaxBenef {

    private Bloc bloc;
    private double prixDeVente;
    private FormeUsuelle formeUsuelle;
    private int quantitePossible;

    public Bloc getBloc() {
        return bloc;
    }

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }

    public double getPrixDeVente() {
        return prixDeVente;
    }

    public void setPrixDeVente(double prixDeVente) {
        this.prixDeVente = prixDeVente;
    }

    public FormeUsuelle getFormeUsuelle() {
        return formeUsuelle;
    }

    public void setFormeUsuelle(FormeUsuelle formeUsuelle) {
        this.formeUsuelle = formeUsuelle;
    }

    public int getQuantitePossible() {
        return quantitePossible;
    }

    public void setQuantitePossible(int quantitePossible) {
        this.quantitePossible = quantitePossible;
    }
}
