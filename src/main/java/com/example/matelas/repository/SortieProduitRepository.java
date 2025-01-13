package com.example.matelas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.matelas.entity.SortieProduit;

public interface SortieProduitRepository extends JpaRepository<SortieProduit, Integer> {

    @Query("select sum(s.quantite) from SortieProduit s where s.achat.id = :idAchat group by s.achat.id ")
    Double findTotalSortieAchat(@Param("idAchat") Long idAchat);
}
