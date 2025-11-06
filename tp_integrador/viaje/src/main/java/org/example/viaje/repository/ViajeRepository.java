package org.example.viaje.repository;

import org.example.viaje.entity.Viaje;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepository extends MongoRepository<Viaje,String> {
    List<Viaje> findByIdUsuario(Long idUsuario);
}
