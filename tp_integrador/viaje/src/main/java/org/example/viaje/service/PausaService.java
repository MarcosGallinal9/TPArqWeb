package org.example.viaje.service;

import org.example.viaje.entity.Pausa;
import org.example.viaje.repository.PausaRepository;
import org.springframework.stereotype.Service;

@Service
public class PausaService {
    PausaRepository pausaRepository;

    public PausaService(PausaRepository pausaRepository) {
        this.pausaRepository = pausaRepository;
    }
    public Pausa save(Pausa pausa){
        return pausaRepository.save(pausa);
    }
    public Pausa findById(String idViaje){
        return pausaRepository.findById(idViaje).orElse(null);
    }
    public Pausa findByIdViaje(String idViaje){
        return pausaRepository.findByIdViaje(idViaje);
    }
}
