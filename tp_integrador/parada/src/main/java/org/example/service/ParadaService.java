package org.example.service;

import org.example.dto.MonopatinDTO;
import org.example.entity.Parada;
import org.example.feignClient.MonopatinFeignClient;
import org.example.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParadaService {

   ParadaRepository paradaRepository;

   MonopatinFeignClient monopatinFeignClient;
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

    /**
     * Lógica de negocio para ubicar un Monopatín en una Parada específica.
     * @param idParada ID de la parada.
     * @param idMonopatin ID del monopatín.
     * @return El DTO del Monopatín actualizado.
     */
    public MonopatinDTO ubicarMonopatin(String idParada, String idMonopatin) {

        // 1. Validar que la parada exista
        Parada parada = paradaRepository.findById(idParada).orElse(null);
        if (parada == null) {
            throw new RuntimeException("Parada no encontrada con ID: " + idParada);
        }

        // 2. Crear DTO para actualizar el monopatín
        MonopatinDTO updateDTO = new MonopatinDTO();
        updateDTO.setId(idMonopatin);
        updateDTO.setIdParadaUbicacion(idParada);
        updateDTO.setEstado("disponible");
        updateDTO.setLatitud(parada.getLatitud());
        updateDTO.setLongitud(parada.getLongitud());

        try {
            // 3. Llamar al microservicio Monopatín (ORQUESTACIÓN)
            return monopatinFeignClient.updateMonopatin(updateDTO);
        } catch (Exception e) {
            // Manejo de errores de comunicación o si el monopatín no existe
            throw new RuntimeException("Error al ubicar el Monopatín en el MS Monopatín: " + e.getMessage());
        }
    }

}
