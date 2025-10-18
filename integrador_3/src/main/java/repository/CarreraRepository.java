package repository;

import entity.Carrera;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import service.CarreraService;

import java.util.List;
import java.util.Optional;


@Repository("CarreraRepository")
public interface CarreraRepository extends BaseJPARepository<Carrera, Long> {


    @Query("""
                SELECT c, COUNT(ec.estudiante)
                FROM Carrera c JOIN EstudianteCarrera ec ON c.carrera = ec.carrera 
                GROUP BY c.id_carrera, c.carrera
                HAVING COUNT(ec.estudiante)>0
                ORDER BY COUNT (ec.estudiante)DESC
    """)
    List<Carrera> getCarrerasConInscriptosOrdenadas();

}

