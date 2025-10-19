package controller;


import entity.EstudianteCarrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import service.EstudianteCarreraService;


import java.util.List;
@RestController
@RequestMapping("/estudianteCarrera")
public class EstudianteCarreraController {

    private final EstudianteCarreraService  estudianteCarreraService;
    @Autowired
    public EstudianteCarreraController(EstudianteCarreraService estudianteCarreraService) {
        this.estudianteCarreraService = estudianteCarreraService;
    }


    @PostMapping
    public EstudianteCarrera matricular(@RequestParam Long dniEstudiante, @RequestParam Long idCarrera) {
        return estudianteCarreraService.matricular(dniEstudiante , idCarrera);
    }

    @GetMapping("/carrera/{idCarrera}")
    public List<EstudianteCarrera> getByCarrera(@PathVariable Long idCarrera,
                                                @RequestParam(required = false) String ciudad) {
        if(ciudad!= null &&!ciudad.isBlank()){
            return estudianteCarreraService.getByCarreraYCiudad(idCarrera, ciudad);
        }
        return estudianteCarreraService.getByCarrera(idCarrera);
    }

}
