package com.example.matelas.model;

import com.example.matelas.entity.Bloc;

public class EtatStockBloc {
    private Bloc bloc;
    private EtatStockBlocMaxBenef etatStockBlocMaxBenef;
    private EtatStockMinPerte etatStockMinPerte;

    public Bloc getBloc() {
        return bloc;
    }

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }

    public EtatStockBlocMaxBenef getEtatStockBlocMaxBenef() {
        return etatStockBlocMaxBenef;
    }

    public void setEtatStockBlocMaxBenef(EtatStockBlocMaxBenef etatStockBlocMaxBenef) {
        this.etatStockBlocMaxBenef = etatStockBlocMaxBenef;
    }

    public EtatStockMinPerte getEtatStockMinPerte() {
        return etatStockMinPerte;
    }

    public void setEtatStockMinPerte(EtatStockMinPerte etatStockMinPerte) {
        this.etatStockMinPerte = etatStockMinPerte;
    }

}
