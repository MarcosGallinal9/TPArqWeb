package org.example.integrador_3.controller;

import org.example.integrador_3.dto.CarreraConInscriptosDTO;
import org.example.integrador_3.dto.CarreraDTO;
import org.example.integrador_3.dto.CarreraReporteDTO;
import org.example.integrador_3.entity.Carrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.integrador_3.service.CarreraService;

import java.util.List;

@RestController
@RequestMapping("api/carrera")
public class CarreraController {

    private final CarreraService carreraService;
    @Autowired
    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try{
            List<CarreraDTO> resultado = carreraService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ninguna carrera.");
        }

    }

    @GetMapping("/inscriptos")
    public ResponseEntity<?> getCarrerasConInscriptosOrdenadas(){
        try{
            List<CarreraConInscriptosDTO> resultado = carreraService.getCarrerasConInscriptosOrdenadas();
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ninguna carrera con inscriptos.");
        }
    }

    @GetMapping("/reportes")
    public ResponseEntity<?> generarReporteCarreras(){
        try{
            List<CarreraReporteDTO> resultado= carreraService.generarReporteCarreras();
            return  ResponseEntity.status(HttpStatus.OK).body(resultado);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ningun reporte de carrera.");
        }

    }

    @PostMapping("/crearCarrera")
    public ResponseEntity<?> crearCarrera(@RequestBody Carrera carrera) {
        try{
            CarreraDTO dto= carreraService.add(carrera);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo ingresar la carrera.");
        }
        }


}
