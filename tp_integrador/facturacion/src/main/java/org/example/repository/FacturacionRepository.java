package org.example.repository;

import org.example.entity.Facturacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturacionRepository extends MongoRepository<Facturacion, Long> {
    List<Facturacion> findByUserId(Long usuarioId);

}
