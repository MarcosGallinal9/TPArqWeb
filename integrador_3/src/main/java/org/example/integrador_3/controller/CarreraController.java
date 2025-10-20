package org.example.integrador_3.controller;

import org.example.integrador_3.dto.CarreraReporteDTO;
import org.example.integrador_3.entity.Carrera;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Carrera> findAll() {
        return carreraService.findAll();
    }

    @GetMapping("/inscriptos")
    public List<Carrera> getCarrerasConInscriptosOrdenadas(){
        return carreraService.getCarrerasConInscriptosOrdenadas();
    }

    @GetMapping("/reportes")
    public List<CarreraReporteDTO> generarReporteCarreras(){
        return carreraService.generarReporteCarreras();
    }

    @PostMapping("/crearCarrera")
    public Carrera crearCarrera(@RequestBody Carrera carrera) {
        return carreraService.add(carrera);
    }


}
