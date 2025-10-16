package controller;

import entity.Carrera;
import entity.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import repository.CarreraRepository;
import repository.EstudianteRepository;
import service.EstudianteService;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    @Qualifier
    @Autowired
    private EstudianteService estudianteService;

    public EstudianteController(@Qualifier("EstudianteService") EstudianteService service){}

    @PostMapping("/crearEstudiante")
    public Estudiante crearEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteService.add(estudiante);
    }

    @GetMapping("/estudiantesOrdenadosEdad")
    public List<Estudiante> findAllByOrderByEdad() {
        return estudianteService.findAllByOrderByEdad();
    }

    @GetMapping("/estudianteLU")
    public Estudiante getByNroLibreta(Integer nroLibreta) {
        return estudianteService.getByNroLibreta(nroLibreta);
    }

    @GetMapping("/estudianteLU")
    public List<Estudiante> getByGenero(String genero) {
        return estudianteService.getByGenero(genero);
    }

}
