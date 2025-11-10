package org.example.tarifa.service;

import org.example.tarifa.entity.Tarifa;
import org.example.tarifa.repository.TarifaRepository;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class TarifaService {

    private TarifaRepository tarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

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

    public Tarifa findById(String id){
        return tarifaRepository.findById(id).orElse(null);
    }

    public Tarifa update(Tarifa tarifa){
        return tarifaRepository.save(tarifa);
    }


}
