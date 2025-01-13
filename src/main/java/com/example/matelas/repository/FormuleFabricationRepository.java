package com.example.matelas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.matelas.entity.Achat;
import com.example.matelas.entity.FormuleFabrication;

public interface FormuleFabricationRepository extends JpaRepository<FormuleFabrication, Integer> {

    @Query("select f from FormuleFabrication f ")
    List<FormuleFabrication> findFormuleFabrication();
}
