package org.example.integrador_3.controller;


import org.example.integrador_3.dto.EstudianteCarreraDTO;
import org.example.integrador_3.entity.EstudianteCarrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.integrador_3.service.EstudianteCarreraService;


import java.util.List;
@RestController
@RequestMapping("api/estudianteCarrera")
public class EstudianteCarreraController {

    private final EstudianteCarreraService  estudianteCarreraService;
    @Autowired
    public EstudianteCarreraController(EstudianteCarreraService estudianteCarreraService) {
        this.estudianteCarreraService = estudianteCarreraService;
    }


    @PostMapping
    public ResponseEntity<?> matricular(@RequestParam Long dniEstudiante, @RequestParam Long idCarrera) {
        try{
            EstudianteCarreraDTO dto= estudianteCarreraService.matricular(dniEstudiante , idCarrera);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al matricular el estudiante.");
        }
    }

    @GetMapping("/carrera/{idCarrera}")
    public ResponseEntity<?> getByCarrera(@PathVariable Long idCarrera,
                                          @RequestParam(required = false) String ciudad) {
        try{
            List<EstudianteCarreraDTO> resultado;
            if(ciudad!= null &&!ciudad.isBlank()){
                resultado = estudianteCarreraService.getByCarreraYCiudad(idCarrera, ciudad);
            }else{
                resultado= estudianteCarreraService.getByCarrera(idCarrera);
            }
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el estudiantes por ciudad y carrera.");
        }

    }

}
