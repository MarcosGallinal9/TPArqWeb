package repository;

import entity.Carrera;
import entity.Estudiante;
import entity.EstudianteCarrera;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import service.EstudianteCarreraService;

import java.util.List;

@Repository
public interface EstudianteCarreraRepository extends BaseJPARepository<EstudianteCarrera, Long>{



    EstudianteCarrera matricular(Estudiante estudiante, Carrera carrera);
}
