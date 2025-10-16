package repository;

import entity.Carrera;
import entity.Estudiante;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import service.EstudianteService;

import java.util.List;

@Repository
public interface EstudianteRepository extends BaseJPARepository<Estudiante, Long>{


    Estudiante save(Estudiante estudiante);

    List<Estudiante> findAll();


    List<Estudiante> findAllByOrderByEdad();


    Estudiante getByNroLibreta(Integer nroLibreta);


    List<Estudiante> getByGenero(String genero);
}
