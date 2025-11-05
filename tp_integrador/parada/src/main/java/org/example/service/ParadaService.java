package org.example.service;

import org.example.entity.Parada;
import org.example.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParadaService {

   ParadaRepository paradaRepository;

    public ParadaService(ParadaRepository paradaRepository) {
        this.paradaRepository = paradaRepository;
    }

    public List<Parada> getAll(){
        return paradaRepository.findAll();
    }

    public Parada save(Parada parada){
        return paradaRepository.save(parada);
    }
    public void delete(Parada parada){
        paradaRepository.delete(parada);
    }

    public Parada findById(String id){
        return paradaRepository.findById(id).orElse(null);
    }

    public Parada update(Parada parada){
        return paradaRepository.save(parada);
    }


}
