package controller;

import entity.Carrera;
import entity.Estudiante;
import entity.EstudianteCarrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import repository.CarreraRepository;
import repository.EstudianteCarreraRepository;
import service.EstudianteCarreraService;
import service.EstudianteService;

import java.util.List;
@RestController
@RequestMapping("/estudianteCarrera")
public class EstudianteCarreraController {
    @Qualifier
    @Autowired
    private EstudianteService  estudianteCarreraService;

    public EstudianteCarreraController(@Qualifier("EstudianteCarreraService") EstudianteCarreraService service) {}

//    @GetMapping("/carreras")
//    public List<EstudianteCarrera> carrerasConEstudiantes() {
//        return EstudianteCarreraRepository.findAll();
//    }

    @PostMapping("/matricularEstudiante")
    public void matricularEstudiante(@RequestBody Estudiante estudiante, Carrera carrera) {
        return estudianteCarreraService.matricular(estudiante, carrera);
    }

}
