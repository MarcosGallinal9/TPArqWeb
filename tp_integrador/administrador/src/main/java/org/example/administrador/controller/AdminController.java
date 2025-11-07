package org.example.administrador.controller;

import org.example.administrador.dto.MonopatinDTO;
import org.example.administrador.dto.ReporteMonopatinXKm;
import org.example.administrador.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/administrador")
public class AdminController {

    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    /**
     * Genera el reporte de uso de monopatines por kilómetros/tiempo. (Punto a.)
     * URL: GET http://localhost:8080/administrador/reportes/mantenimiento-uso
     */
    @GetMapping("/reportes/mantenimiento-uso")
    public ResponseEntity<List<ReporteMonopatinXKm>> getReporteMonopatines() {
        List<ReporteMonopatinXKm> reportes = adminService.generarReporteUso();
        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reportes);
    }
}
