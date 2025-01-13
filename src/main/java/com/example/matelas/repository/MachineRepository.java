package com.example.matelas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.matelas.entity.Machine;

public interface MachineRepository extends JpaRepository<Machine, Integer> {

}
