package org.example.integrador_3.controller;


import org.example.integrador_3.entity.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.example.integrador_3.service.EstudianteService;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {
    @Qualifier
    private final EstudianteService estudianteService;
    @Autowired
    public EstudianteController(EstudianteService estudianteService){
        this.estudianteService = estudianteService;
    }


    @PostMapping("/crearEstudiante")
    public Estudiante crearEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteService.add(estudiante);
    }

    @GetMapping
    public List<Estudiante> getAll(@RequestParam(required = false) String orden){
        if("edad".equalsIgnoreCase(orden)){
            return estudianteService.findAllByOrderByEdad();
        }
        return estudianteService.findAll();
    }


    @GetMapping("/{nroLibreta}")
    public Estudiante getByNroLibreta(@PathVariable Integer nroLibreta) {
        return estudianteService.getByNroLibreta(nroLibreta);
    }

    @GetMapping("/genero/{genero}")
    public List<Estudiante> getByGenero(@PathVariable String genero) {

        return estudianteService.getByGenero(genero);
    }

}
