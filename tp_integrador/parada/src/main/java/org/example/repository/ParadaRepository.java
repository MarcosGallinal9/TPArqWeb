package org.example.repository;

import org.example.entity.Parada;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepository extends MongoRepository<Parada,String> {

}
