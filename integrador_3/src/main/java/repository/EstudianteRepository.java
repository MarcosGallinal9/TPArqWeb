package repository;

import entity.Carrera;
import entity.Estudiante;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends BaseJPARepository<Carrera, Long>{

}
