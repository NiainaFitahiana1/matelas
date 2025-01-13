package com.example.matelas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.matelas.entity.Bloc;

public interface BlocRepository extends JpaRepository<Bloc, Integer> {

    @Query("SELECT b FROM Bloc b " +
            "WHERE b.id NOT IN (SELECT t.bloc.id FROM Transformation t)")
    List<Bloc> getAllBlocDisponible();

    @Query("select b from Bloc b where b.source.id = :id")
    Bloc findBlocFille(@Param("id") Long idBloc);

    @Query("SELECT b FROM Bloc b where b.machine.id = :idMachine")
    List<Bloc> findBlocByMachine(@Param("idMachine") Long idMachine);

}
