package org.example.integrador_3.service;

import jakarta.transaction.Transactional;
import org.example.integrador_3.dto.CarreraConInscriptosDTO;
import org.example.integrador_3.dto.CarreraDTO;
import org.example.integrador_3.dto.CarreraReporteDTO;
import org.example.integrador_3.entity.Carrera;
import org.example.integrador_3.repository.EstudianteCarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.integrador_3.repository.CarreraRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraService{

    private CarreraRepository carreraRepository;


    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Transactional
    public List<CarreraConInscriptosDTO> getCarrerasConInscriptosOrdenadas() throws  Exception {
        try{
            return carreraRepository.getCarrerasConInscriptosOrdenadas();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<CarreraDTO> findAll() throws Exception{
        try{
            return carreraRepository.findAll()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new Exception(e);
        }

    }
    @Transactional
    public List<CarreraReporteDTO> generarReporteCarreras() throws Exception{
        try{
            return carreraRepository.generarReporteCarreras();
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public CarreraDTO add(Carrera carrera) throws Exception {
        try{
            Carrera saved= carreraRepository.save(carrera);
            return toDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CarreraDTO toDTO(Carrera carrera){
        if(carrera == null){
            return null;
        }
        return new CarreraDTO(carrera.getIdCarrera(), carrera.getCarrera(), carrera.getDuracion());
    }
}
