package service;

import dto.EstudianteCarreraDTO;
import entity.Carrera;
import entity.Estudiante;
import entity.EstudianteCarrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.EstudianteCarreraRepository;

@Service
public class EstudianteCarreraService implements BaseService<EstudianteCarrera>{
    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;

    public EstudianteCarrera matricular(Estudiante estudiante, Carrera carrera) {
        //VER ID AUTOINCREMENTAL
        EstudianteCarrera matriculado= new EstudianteCarrera(estudiante,carrera);
        return estudianteCarreraRepository.save(matriculado);
    }

}
