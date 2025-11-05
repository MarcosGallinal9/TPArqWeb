package org.example.monopatin.controller;

import org.example.monopatin.entity.Monopatin;
import org.example.monopatin.service.MonopatinService;
import org.springframework.beans.factory.annotation.Autowired;
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


}
