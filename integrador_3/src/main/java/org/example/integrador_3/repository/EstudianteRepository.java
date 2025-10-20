package org.example.integrador_3.repository;

import org.example.integrador_3.entity.Carrera;
import org.example.integrador_3.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {


   List<Estudiante> findAllByOrderByEdad();


    Estudiante getByNroLibreta(Integer nroLibreta);


    List<Estudiante> getByGenero(String genero);

    Estudiante save(Estudiante estudiante);
    Estudiante findByDni(Integer id);
}
