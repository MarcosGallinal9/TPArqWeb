package org.example.repository;

import feign.Param;
import org.example.entity.Facturacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FacturacionRepository extends MongoRepository<Facturacion, Long> {
    List<Facturacion> findByUserId(Long usuarioId);

    @Query(value = "{ 'fecha': { $gte: ?0, $lte: ?1 } }", fields = "{ 'total': 1 }")
    double findByFechaBetween(Date inicio, Date fin);
}
