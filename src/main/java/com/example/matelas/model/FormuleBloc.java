package com.example.matelas.model;

import java.util.List;

import com.example.matelas.entity.FormuleFabrication;

public class FormuleBloc {

    List<FormuleFabrication> produits;

    public List<FormuleFabrication> getProduits() {
        return produits;
    }

    public void setProduits(List<FormuleFabrication> produits) {
        this.produits = produits;
    }

}