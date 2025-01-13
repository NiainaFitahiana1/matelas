package com.example.matelas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.matelas.entity.Bloc;
import com.example.matelas.entity.Machine;
import com.example.matelas.model.MachineTrier;
import com.example.matelas.repository.BlocRepository;
import com.example.matelas.repository.MachineRepository;

@Service
public class MachineService {
    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private BlocService blocService;

    public List<Machine> getAllMachine() {
        return machineRepository.findAll();
    }

    public double getEcartTotalPrixDeRevientBlocByMachine(Machine machine) {
        List<Bloc> blocs = blocService.getAllBlocByMachine(machine);
        double totalEcart = 0;
        for (int i = 0; i < blocs.size(); i++) {
            totalEcart = totalEcart + blocService.getEcartPrixDeRevientBloc(blocs.get(i));
        }
        return totalEcart;
    }

    public List<MachineTrier> trierMachine() {
        List<MachineTrier> machineTriers = new ArrayList<>();

        List<Machine> machines = getAllMachine();
        for (int i = 0; i < machines.size(); i++) {
            MachineTrier machineTrier = new MachineTrier();
            machineTrier.setMachine(machines.get(i));
            machineTrier.setTotalEcart(getEcartTotalPrixDeRevientBlocByMachine(machines.get(i)));
            machineTriers.add(machineTrier);
        }
        // List<MachineTrier> res = new ArrayList<>();
        // for (int i = 0; i < machineTriers.size(); i++) {
        // }
        return machineTriers;
    }
}
