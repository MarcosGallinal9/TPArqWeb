package org.example.viaje.repository;

import org.example.viaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje,Long> {
    List<Viaje> findByUserId(Long userId);
}
