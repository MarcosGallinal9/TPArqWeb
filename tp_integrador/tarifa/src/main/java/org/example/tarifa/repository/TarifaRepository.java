package org.example.tarifa.repository;

import org.example.tarifa.entity.Tarifa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends MongoRepository<Tarifa, Long> {

}
