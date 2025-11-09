package org.example.monopatin.controller;

import org.example.monopatin.entity.Monopatin;
import org.example.monopatin.service.MonopatinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    MonopatinService monopatinService;

    public MonopatinController(MonopatinService monopatinService) {
        this.monopatinService = monopatinService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Monopatin>> getAllMonopatines() {
        List<Monopatin> monopatines = monopatinService.getAll();
        if (monopatines.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(monopatines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monopatin> getMonopatinById(@PathVariable("id") String id) {
        Monopatin monopatin = monopatinService.findById(id);
        if (monopatin == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(monopatin);
    }

    @PostMapping("")
    public ResponseEntity<Monopatin> save(@RequestBody Monopatin monopatin) {
        Monopatin monopatinNew = monopatinService.save(monopatin);
        return ResponseEntity.ok(monopatinNew);
    }

    @PutMapping("/{id}/evaluar-mantenimiento")
    public ResponseEntity<String> evaluarMantenimiento(@PathVariable("id") String id) {
        try {
            monopatinService.evaluarMantenimiento(id);
            return ResponseEntity.ok("Evaluación de mantenimiento para monopatín " + id + " completada.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/cercanos")
    public  List<Monopatin> getMonpatinesCercanos(@RequestParam("lat") double lat, @RequestParam("lng") double lng, @RequestParam(value = "radiokm", defaultValue = "1.0") double radiokm) {
        return monopatinService.buscarCercanos(lat, lng, radiokm);
    }



}
