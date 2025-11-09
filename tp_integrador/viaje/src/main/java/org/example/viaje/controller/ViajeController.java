package org.example.viaje.controller;

import org.example.viaje.dto.ReporteMonopatinContadorViajes;
import org.example.viaje.dto.ReporteUsoDTO;
import org.example.viaje.entity.Viaje;
import org.example.viaje.service.ViajeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<List<Viaje>> getViajesByUserId(@PathVariable("userId") String userId) {
        List<Viaje> viajes = viajeService.byUserId(userId);
        return ResponseEntity.ok(viajes);
    }

    /**
     * Inicia un viaje. Llama al Monopatín para ponerlo en uso.
     * URL: POST http://localhost:8083/viajes/iniciar
     */
    @PostMapping("/iniciar")
    public ResponseEntity<Viaje> iniciarViaje(@RequestBody Viaje viaje) {
        try {
            Viaje viajeNuevo = viajeService.iniciarViaje(viaje);
            return ResponseEntity.status(201).body(viajeNuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Finaliza un viaje. Llama a Parada para validar ubicación, actualiza Monopatín y notifica a Facturación.
     * URL: PUT http://localhost:8083/viajes/finalizar/{id}
     */
    @PutMapping("/finalizar/{id}")
    public ResponseEntity<Viaje> finalizarViaje(
            @PathVariable("id") String idViaje,
            @RequestParam("idParadaFin") String idParadaFin,
            @RequestParam("kmRecorridos") float kmRecorridosFinal) {

        try {
            Viaje viajeFinalizado = viajeService.finalizarViaje(idViaje, idParadaFin, kmRecorridosFinal);
            return ResponseEntity.ok(viajeFinalizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint consultado por Administrador para generar el reporte de monopatines más usados.
     * URL: GET /viajes/reportes/monopatines-por-viajes?year={year}
     */
    @GetMapping("/reportes/monopatines-por-viajes")
    public ResponseEntity<List<ReporteMonopatinContadorViajes>> getMonopatinesPorViajes(@RequestParam("year") int year) {

        List<ReporteMonopatinContadorViajes> reporte = viajeService.getConteoViajesPorMonopatinYAnio(year);

        if (reporte.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("reporte/uso")
    public ReporteUsoDTO getReporteUso(@RequestParam("userIds") List<String> userIds, @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fechaInicio, @RequestParam("fechaFin") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE )LocalDate fechaFin) {
        return viajeService.getReporteUsoDto(userIds, fechaInicio, fechaFin);
    }
}
