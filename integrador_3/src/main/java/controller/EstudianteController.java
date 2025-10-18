package controller;


import entity.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import service.EstudianteService;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {
    @Qualifier
    @Autowired
    private EstudianteService estudianteService;

    public EstudianteController(@Qualifier("EstudianteService") EstudianteService service){}


    @PostMapping("/crearEstudiante")
    public Estudiante crearEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteService.add(estudiante);
    }
    // /api/estudiantes?orden=edad
    @GetMapping
    public List<Estudiante> getAll(@RequestParam(required = false) String orden){
        if("edad".equalsIgnoreCase(orden)){
            return estudianteService.findAllByOrderByEdad();
        }
        return estudianteService.findAll();
    }

//    @GetMapping("/estudiantesOrdenadosEdad")
//    public List<Estudiante> findAllByOrderByEdad() {
//        return estudianteService.findAllByOrderByEdad();
//    }


    @GetMapping("/{nroLibreta}")
    public Estudiante getByNroLibreta(@PathVariable Integer nroLibreta) {
        return estudianteService.getByNroLibreta(nroLibreta);
    }

    @GetMapping("/genero/{genero}")
    public List<Estudiante> getByGenero(@PathVariable String genero) {
        return estudianteService.getByGenero(genero);
    }

}
