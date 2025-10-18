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

List<EstudianteCarrera> getByCarrera_Id(Integer carrera_id);

List<EstudianteCarrera> GetByCarrera_NombreYEstudiante_CiudadResidencia(String carrera_nombre, String ciudad);


}
