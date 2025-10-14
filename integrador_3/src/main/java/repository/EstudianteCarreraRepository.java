package repository;

import entity.Carrera;
import entity.EstudianteCarrera;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteCarreraRepository extends BaseJPARepository<Carrera, Long>{

}
