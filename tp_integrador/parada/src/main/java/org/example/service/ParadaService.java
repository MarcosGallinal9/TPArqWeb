package org.example.service;

import org.example.entity.Parada;
import org.example.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParadaService {

    @Autowired
    ParadaRepository paradaRepository;

    public List<Parada> getAll(){
        return paradaRepository.findAll();
    }

    public Parada save(Parada parada){
        Parada paradaNew;
        paradaNew = paradaRepository.save(parada);
        return paradaNew;
    }
    public void delete(Parada parada){

        paradaRepository.delete(parada);
    }

    public Parada findById(Long id){
        return paradaRepository.findById(id).orElse(null);
    }

    public Parada update(Parada car){
        return paradaRepository.save(car);
    }

    public List<Parada> byUserId(Long userid){
        return paradaRepository.findByUserId(userid);
    }
}
