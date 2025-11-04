package org.example.repository;

import org.example.entity.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturacionRepository extends JpaRepository<Facturacion, Long> {
    List<Facturacion> findByUserId(Long usuarioId);

}
