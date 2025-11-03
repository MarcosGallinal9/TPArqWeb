package org.example.repository;

import org.example.entity.Parada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepository extends JpaRepository<Parada,Long> {

    List<Parada> findByUserId(Long userId);
}
