package org.example.controller;

import org.example.entity.Parada;
import org.example.service.ParadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paradas")
public class ParadaController {
    ParadaService paradaService;

    public ParadaController(ParadaService paradaService) {
        this.paradaService = paradaService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Parada>> getAllParadas() {
        List<Parada> paradas = paradaService.getAll();
        if (paradas.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(paradas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parada> getParadaById(@PathVariable("id") String id) {
        Parada parada = paradaService.findById(id);
        if (parada == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(parada);
    }

    @PostMapping("")
    public ResponseEntity<Parada> save(@RequestBody Parada parada) {
        Parada paradaNew = paradaService.save(parada);
        return ResponseEntity.ok(paradaNew);
    }


}
