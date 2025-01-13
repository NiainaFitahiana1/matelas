package com.example.matelas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.matelas.entity.Achat;

public interface AchatRepository extends JpaRepository<Achat, Integer> {

    @Query("select a from Achat a where a.produit.id = :idProduit and a.dateAchat <= :date and a.isAvailable = true order by a.dateAchat asc")
    List<Achat> findAchatUtiliseByDate(@Param("date") LocalDateTime date, @Param("idProduit") Long idProduit);
}
