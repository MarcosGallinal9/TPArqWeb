package org.example.integrador_3.repository;

import org.example.integrador_3.dto.CarreraConInscriptosDTO;
import org.example.integrador_3.dto.CarreraReporteDTO;
import org.example.integrador_3.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("CarreraRepository")
public interface CarreraRepository extends JpaRepository<Carrera, Long> {



    @Query("""
                SELECT new org.example.integrador_3.dto.CarreraConInscriptosDTO(
                                                          c.idCarrera,
                                                          c.carrera,
                                                          c.duracion,
                                                          COUNT(ec.estudiante)
                                                      )
                                                      FROM Carrera c
                                                      JOIN c.estudiantesCarrera ec
                                                      GROUP BY c.idCarrera, c.carrera, c.duracion
                                                      HAVING COUNT(ec.estudiante) > 0
                                                      ORDER BY COUNT(ec.estudiante) DESC
    """)
    List<CarreraConInscriptosDTO> getCarrerasConInscriptosOrdenadas();

    @Query("""
               SELECT new org.example.integrador_3.dto.CarreraReporteDTO(
                                                                    c.carrera,
                                                                    ec.inscripcion,
                                                                    COUNT(ec.estudiante),
                                                                    SUM(CASE WHEN ec.graduacion IS NOT NULL THEN 1 ELSE 0 END)
                                                                )
                                                                FROM Carrera c
                                                                JOIN c.estudiantesCarrera ec
                                                                GROUP BY c.carrera, ec.inscripcion
                                                                ORDER BY c.carrera ASC, ec.inscripcion ASC
          """)
    List<CarreraReporteDTO> generarReporteCarreras();

}

