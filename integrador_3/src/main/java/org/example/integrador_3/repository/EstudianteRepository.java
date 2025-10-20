package org.example.integrador_3.repository;

import org.example.integrador_3.entity.Estudiante;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends BaseJPARepository<Estudiante, Long>{


   List<Estudiante> findAllByOrderByEdad();


    Estudiante getByNroLibreta(Integer nroLibreta);


    List<Estudiante> getByGenero(String genero);
}
