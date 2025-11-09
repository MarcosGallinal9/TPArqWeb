package org.example.monopatin.repository;

import org.example.monopatin.entity.Monopatin;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MonopatinRepository extends MongoRepository<Monopatin, String> {

    List<Monopatin>findByEstadoAndUbicacionNear(String estado, Point ubicacionUsuario, Distance radio);


}
