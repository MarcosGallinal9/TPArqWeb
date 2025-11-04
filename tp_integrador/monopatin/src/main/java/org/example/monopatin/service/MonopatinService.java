package org.example.monopatin.service;

import org.example.monopatin.entity.Monopatin;
import org.example.monopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonopatinService {
    @Autowired
    MonopatinRepository monopatinRepository;

    public List<Monopatin> getAll(){

        return monopatinRepository.findAll();
    }

    public Monopatin save(Monopatin monopatin){
        Monopatin monopatinNew;
        monopatinNew = monopatinRepository.save(monopatin);
        return monopatinNew;
    }
    public void delete(Monopatin monopatin){
        monopatinRepository.delete(monopatin);
    }

    public Monopatin findById(Long id){
        return monopatinRepository.findById(id).orElse(null);
    }

    public Monopatin update(Monopatin bike){
        return monopatinRepository.save(bike);
    }

    public List<Monopatin> byUserId(Long userid){
        return monopatinRepository.findByUserId(userid);
    }
}
