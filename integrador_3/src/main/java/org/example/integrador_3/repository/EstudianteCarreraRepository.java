package org.example.integrador_3.repository;


import org.example.integrador_3.entity.Carrera;
import org.example.integrador_3.entity.EstudianteCarrera;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstudianteCarreraRepository extends BaseJPARepository<EstudianteCarrera, Long>{

    List<EstudianteCarrera> getByCarrera_IdCarrera(Long idCarrera);

    @Query("""
        SELECT ec
        FROM EstudianteCarrera ec
        WHERE ec.carrera.carrera = :nombreCarrera
          AND ec.estudiante.ciudadResidencia = :ciudad
    """)
    List<EstudianteCarrera> getByCarreraYCiudad(@Param("nombreCarrera") String nombreCarrera,
                                                @Param("ciudad") String ciudad);


    Long countByCarrera(Carrera carrera);
}
