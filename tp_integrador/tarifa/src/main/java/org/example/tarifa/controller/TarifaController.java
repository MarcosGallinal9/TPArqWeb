package org.example.tarifa.controller;


import org.example.tarifa.entity.Tarifa;
import org.example.tarifa.service.TarifaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {
    TarifaService tarifaService;

    @GetMapping("/")
    public ResponseEntity<List<Tarifa>> getAllTarifas() {
        List<Tarifa> tarifas = tarifaService.getAll();
        if (tarifas.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tarifas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> getTarifasById(@PathVariable("id") String id) {
        Tarifa tarifa = tarifaService.findById(id);
        if (tarifa == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tarifa);
    }

    @PostMapping("")
    public ResponseEntity<Tarifa> save(@RequestBody Tarifa tarifa) {
        Tarifa tarifaNew = tarifaService.save(tarifa);
        return ResponseEntity.ok(tarifaNew);
    }


}
