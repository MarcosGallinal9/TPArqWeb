package org.example.administrador.controller;

import org.example.administrador.dto.CuentaDTO;
import org.example.administrador.dto.MonopatinDTO;
import org.example.administrador.dto.ReporteMonopatinContadorViajes;
import org.example.administrador.dto.ReporteMonopatinXKm;
import org.example.administrador.feingClients.FacturacionFeingClient;
import org.example.administrador.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrador")
public class AdminController {

    AdminService adminService;
    FacturacionFeingClient facturacionFeingClient;

    public AdminController(AdminService adminService
    , FacturacionFeingClient facturacionFeingClient) {
        this.adminService = adminService;
        this.facturacionFeingClient = facturacionFeingClient;
    }
    /**
     * PUNTO A
     * Genera el reporte de uso de monopatines por kilómetros/tiempo.
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

    /**
     * PUNTO B
     * Anula una cuenta de usuario.
     * URL: PUT http://localhost:8080/administrador/cuentas/anular/{id}
     */

    @PutMapping("/cuentas/anular/{idCuenta}")
    public ResponseEntity<Void> anularCuenta(@PathVariable("idCuenta") String idCuenta) {
        try {
            adminService.anularCuentaUsuario(idCuenta);
            // 204 No Content para indicar éxito sin cuerpo de respuesta.
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * PUNTO C
     * Consulta los monopatines con más de X viajes en un cierto año.
     * URL: GET /administrador/reportes/top-monopatines?minViajes={X}&year={Año}
     */
    @GetMapping("/reportes/top-monopatines")
    public ResponseEntity<List<ReporteMonopatinContadorViajes>> getMonopatinesConMasDeXViajes(
                                                            @RequestParam("minViajes") int minViajes,
                                                            @RequestParam("year") int year) {

        List<ReporteMonopatinContadorViajes> topMonopatines = adminService.getMonopatinesConMasDeXViajes(minViajes, year);

        if (topMonopatines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(topMonopatines);
    }

    /**
     * PUNTO D
     * Consulta el total facturado en un rango de meses en un cierto año-
     * URL: GET /administrador/total-facturado?anio={X}&mesInicio={X}&mesFin={X}
     */
    @GetMapping("/total-facturado")
    public Double getTotalFacturado(
            @RequestParam int anio,
            @RequestParam int mesInicio,
            @RequestParam int mesFin) {
        return facturacionFeingClient.getTotalFacturado(anio, mesInicio, mesFin).getBody();
    }

}
