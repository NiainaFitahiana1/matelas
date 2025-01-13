package com.example.matelas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.matelas.entity.Bloc;
import com.example.matelas.entity.DetailBloc;

public interface DetailBlocRepository extends JpaRepository<DetailBloc, Integer> {
    @Query("select d from DetailBloc d where d.produit.id = :idProduit and d.bloc.id = :idBloc")
    DetailBloc findDetailBloc(@Param("idProduit") Long idProduit, @Param("idBloc") Long idBloc);
}
