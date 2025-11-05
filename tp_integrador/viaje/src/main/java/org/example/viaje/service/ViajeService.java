package org.example.viaje.service;

import org.example.viaje.entity.Viaje;
import org.example.viaje.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ViajeService {

    ViajeRepository viajeRepository;

    public ViajeService(ViajeRepository viajeRepository) {
        this.viajeRepository = viajeRepository;
    }

    public List<Viaje> getAll(){
        return viajeRepository.findAll();
    }

    public Viaje save(Viaje viaje){
        return viajeRepository.save(viaje);

    }
    public void delete(Viaje viaje){
        viajeRepository.delete(viaje);
    }

    public Viaje findById(String id){
        return viajeRepository.findById(id).orElse(null);
    }

    public Viaje update(Viaje viaje){
        return viajeRepository.save(viaje);
    }

    public List<Viaje> byUserId(Long userid){
        return viajeRepository.findByIdUsuario(userid);
    }
}
