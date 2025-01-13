package com.example.matelas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.matelas.model.EtatStockBloc;
import com.example.matelas.model.EtatStockFormeUsuelle;
import com.example.matelas.service.EtatDeStockBlocService;
import com.example.matelas.service.EtatDeStockFormeUsuelleService;
import com.example.matelas.service.FormeUsuelleService;

import org.springframework.ui.Model;

@Controller
public class StockController {
    @Autowired
    private EtatDeStockFormeUsuelleService etatDeStockFormeUsuelleService;

    @Autowired
    private EtatDeStockBlocService etatDeStockBlocService;

    @GetMapping("/etatstock")
    public String getEtatDeStock(Model model) {

        List<EtatStockFormeUsuelle> etatStockFormeUsuelles = etatDeStockFormeUsuelleService
                .getAllEtatDeStockFormeUsuells();

        List<EtatStockBloc> etatStockBlocs = etatDeStockBlocService.getAllEtatDeStockBloc();
        model.addAttribute("etatStockFormeUsuelles", etatStockFormeUsuelles);
        model.addAttribute("etatStockBlocs", etatStockBlocs);
        return "etatDeStock";
    }

}
