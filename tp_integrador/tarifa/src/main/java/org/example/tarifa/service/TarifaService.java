package org.example.tarifa.service;

import org.example.tarifa.entity.Tarifa;
import org.example.tarifa.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TarifaService {
    @Autowired
    private TarifaRepository tarifaRepository;

    public List<Tarifa> getAll(){

        return tarifaRepository.findAll();
    }

    public Tarifa save(Tarifa tarifa){
        Tarifa tarifaNew;
        tarifaNew = tarifaRepository.save(tarifa);
        return tarifaNew;
    }
    public void delete(Tarifa tarifa){
        tarifaRepository.delete(tarifa);
    }

    public Tarifa findById(Long id){
        return tarifaRepository.findById(id).orElse(null);
    }

    public Tarifa update(Tarifa tarifa){
        return tarifaRepository.save(tarifa);
    }


}
