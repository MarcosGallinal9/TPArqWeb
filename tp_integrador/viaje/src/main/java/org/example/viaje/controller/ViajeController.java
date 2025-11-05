package org.example.viaje.controller;

import org.example.viaje.entity.Viaje;
import org.example.viaje.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/viajes")
public class ViajeController {

    ViajeService viajeService;

    public ViajeController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Viaje>> getAllViajes() {
        List<Viaje> viajes = viajeService.getAll();
        if (viajes.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(viajes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viaje> getViajeById(@PathVariable("id") String id) {
        Viaje viaje = viajeService.findById(id);
        if (viaje == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(viaje);
    }

    @PostMapping("")
    public ResponseEntity<Viaje> save(@RequestBody Viaje viaje) {
        Viaje viajeNew = viajeService.save(viaje);
        return ResponseEntity.ok(viajeNew);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Viaje>> getViajesByUserId(@PathVariable("userId") Long userId) {
        List<Viaje> viajes = viajeService.byUserId(userId);
        return ResponseEntity.ok(viajes);
    }
}
