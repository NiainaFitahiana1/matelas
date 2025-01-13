package com.example.matelas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.matelas.entity.Bloc;
import com.example.matelas.entity.FormeUsuelle;

public interface FormeUsuelleRepository extends JpaRepository<FormeUsuelle, Integer> {
    @Query("SELECT f FROM FormeUsuelle f ORDER BY (f.prixVente / (f.largeur * f.longueur * f.hauteur)) DESC limit 1")
    FormeUsuelle findFormeUsuelleWithBestPriceToVolumeRatio();

    @Query("SELECT f FROM FormeUsuelle f ORDER BY (f.largeur * f.longueur * f.hauteur) ASC limit 1")
    FormeUsuelle findDFormeUsuelleWithMinVolume();
}
