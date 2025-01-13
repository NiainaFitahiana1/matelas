package com.example.matelas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.matelas.entity.Bloc;
import com.example.matelas.model.EtatStockBloc;
import com.example.matelas.repository.BlocRepository;

@Service
public class EtatDeStockBlocService {
    @Autowired
    BlocRepository blocRepository;

    @Autowired
    BlocService blocService;

    public EtatStockBloc getEtatDeStock(Bloc bloc) {
        EtatStockBloc etatStockBloc = new EtatStockBloc();

        etatStockBloc.setBloc(bloc);
        etatStockBloc.setEtatStockBlocMaxBenef(blocService.getEtatStockBlocMaxBenef(bloc));
        etatStockBloc.setEtatStockMinPerte(blocService.getEtatStockBlocMinPerte(bloc));

        return etatStockBloc;
    }

    public List<EtatStockBloc> getAllEtatDeStockBloc() {
        List<Bloc> blocDisponible = blocService.getAllBlocDisponible();

        List<EtatStockBloc> etatStockBlocs = new ArrayList<>();

        for (int i = 0; i < blocDisponible.size(); i++) {
            EtatStockBloc etat = getEtatDeStock(blocDisponible.get(i));
            etatStockBlocs.add(etat);
        }
        return etatStockBlocs;

    }

}
