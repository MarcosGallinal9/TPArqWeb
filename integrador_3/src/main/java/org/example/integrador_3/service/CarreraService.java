package org.example.integrador_3.service;

import org.example.integrador_3.dto.CarreraReporteDTO;
import org.example.integrador_3.entity.Carrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.integrador_3.repository.CarreraRepository;

import java.util.List;

@Service
public class CarreraService implements BaseService<Carrera>{
    @Autowired
    private CarreraRepository carreraRepository;

    public List<Carrera> getCarrerasConInscriptosOrdenadas() {
        return carreraRepository.getCarrerasConInscriptosOrdenadas();
    }

    public List<Carrera> findAll() {
        return carreraRepository.findAll();
    }

    public List<CarreraReporteDTO> generarReporteCarreras() {
        return carreraRepository.generarReporteCarreras();
    }

    public Carrera add(Carrera carrera) {
        return carreraRepository.save(carrera);
    }
}
