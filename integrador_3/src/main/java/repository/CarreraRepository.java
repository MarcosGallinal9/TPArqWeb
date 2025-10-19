package repository;

import dto.CarreraReporteDTO;
import entity.Carrera;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import service.CarreraService;

import java.util.List;
import java.util.Optional;


@Repository("CarreraRepository")
public interface CarreraRepository extends BaseJPARepository<Carrera, Long> {



    @Query("""
                SELECT c
                FROM Carrera c
                JOIN c.estudiantesCarrera ec
                GROUP BY c.idCarrera, c.carrera
                HAVING COUNT(ec.estudiante) > 0
                ORDER BY COUNT(ec.estudiante) DESC
    """)
    List<Carrera> getCarrerasConInscriptosOrdenadas();

    @Query("""
               SELECT new dto.CarreraReporteDTO(
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

