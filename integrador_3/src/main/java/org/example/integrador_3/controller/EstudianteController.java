package org.example.integrador_3.controller;


import org.example.integrador_3.dto.EstudianteDTO;
import org.example.integrador_3.entity.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> crearEstudiante(@RequestBody Estudiante estudiante) {
        try{
            EstudianteDTO dto= estudianteService.add(estudiante);
            return  ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear estudiante");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String orden){
        try{
            List<EstudianteDTO> resultado;
            if("edad".equalsIgnoreCase(orden)){
                resultado = estudianteService.findAllByOrderByEdad();
            }else{
                resultado= estudianteService.findAll();
            }
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al buscar estudiantes");
        }
    }


    @GetMapping("/{nroLibreta}")
    public ResponseEntity<?> getByNroLibreta(@PathVariable Integer nroLibreta) {
        try{
            EstudianteDTO dto= estudianteService.getByNroLibreta(nroLibreta);
            return  ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al buscar estudiante");
        }
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> getByGenero(@PathVariable String genero) {
        try{
            List<EstudianteDTO> resultado= estudianteService.getByGenero(genero);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al buscar estudiantes");
        }

    }

}
