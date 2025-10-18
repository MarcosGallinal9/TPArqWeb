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
    private EstudianteCarreraService  estudianteCarreraService;

    public EstudianteCarreraController(@Qualifier("EstudianteCarreraService") EstudianteCarreraService service) {}


    @PostMapping
    public void matricular(@RequestParam int dniEstudiante @RequestParam int idCarrera) {
        return estudianteCarreraService.matricular(dniEstudiante , idCarrera);
    }

    @GetMapping("/carrera/{idCarrera}")
    public List<EstudianteCarrera> getByCarrera(@PathVariable String idCarrera,
                                                @RequestParam(required = false) String ciudad) {
        if (ciudad != null && !ciudad.isBlank()) {
            return estudianteCarreraService.getByCarreraYCiudad(
                    estudianteCarreraService.getByCarrera(idCarrera).stream().filter(ec -> ciudad.equalsIgnoreCase(ec.getEstudiante().getCiudadResidencia())).toList()
            );
        }
        return estudianteCarreraService.getByCarrera(idCarrera);
    }

}
