package org.example.monopatin.service;

import org.example.monopatin.entity.Monopatin;
import org.example.monopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonopatinService {

    MonopatinRepository monopatinRepository;

    public MonopatinService(MonopatinRepository monopatinRepository) {
        this.monopatinRepository = monopatinRepository;
    }

    public List<Monopatin> getAll(){
        return monopatinRepository.findAll();
    }
    public Monopatin save(Monopatin monopatin){
        return monopatinRepository.save(monopatin);
    }
    public void delete(Monopatin monopatin){
        monopatinRepository.delete(monopatin);
    }

    public Monopatin findById(String id){
        return monopatinRepository.findById(id).orElse(null);
    }

    public Monopatin update(Monopatin monopatin){
        return monopatinRepository.save(monopatin);
    }

}
