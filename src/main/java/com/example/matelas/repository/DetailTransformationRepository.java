package com.example.matelas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.matelas.entity.DetailTransformation;

public interface DetailTransformationRepository extends JpaRepository<DetailTransformation, Integer> {

    @Query("select dt from DetailTransformation dt where dt.formeUsuelle.id = :id")
    List<DetailTransformation> findDetailTransformationByIdFormeUsuelle(@Param("id") Long id);

    @Query("select dt from DetailTransformation dt where dt.transformation.bloc.id = :id")
    List<DetailTransformation> findDetailTransformationByBloc(@Param("id") Integer id);
}
