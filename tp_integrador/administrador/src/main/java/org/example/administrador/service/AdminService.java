package org.example.administrador.service;

import feign.FeignException;
import org.example.administrador.dto.*;
import org.example.administrador.entity.Admin;
import org.example.administrador.feingClients.*;
import org.example.administrador.repository.AdminRepository;
import org.example.administrador.dto.ReporteUsoDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    //Feing clients
    MonopatinFeingClient monopatinFeingClient;
    ViajeFeingClient viajeFeingClient;
    CuentaFeingClient cuentaFeingClient;
    FacturacionFeingClient facturacionFeingClient;
    UsuarioFeingClient usuarioFeingClient;
    TarifaFeingClient tarifaFeingClient;
    
    AdminRepository adminRepository;

    public AdminService(MonopatinFeingClient monopatinFeingClient, ViajeFeingClient viajeFeingClient, CuentaFeingClient cuentaFeingClient, FacturacionFeingClient facturacionFeingClient, UsuarioFeingClient usuarioFeingClient, TarifaFeingClient tarifaFeingClient, AdminRepository adminRepository) {
        this.monopatinFeingClient = monopatinFeingClient;
        this.viajeFeingClient = viajeFeingClient;
        this.cuentaFeingClient = cuentaFeingClient;
        this.facturacionFeingClient = facturacionFeingClient;
        this.usuarioFeingClient = usuarioFeingClient;
        this.tarifaFeingClient = tarifaFeingClient;
        this.adminRepository = adminRepository;
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    /**
     * PUNTO A
     * Genera el reporte consolidado de uso (Km, Tiempo Neto y Tiempo Total) para todos los monopatines.
     * Cumple con el requisito: "Este reporte debe poder configurarse para incluir (o no) los tiempos de pausa."
     *
     * @return Lista de ReporteMonopatinXKm con las métricas.
     */
    public List<ReporteMonopatinXKm> generarReporteUso(boolean pausas) {

        // Manejo de Feign y obtención de monopatines (Se mantiene)
        ResponseEntity<List<MonopatinDTO>> response;
        try {
            response = monopatinFeingClient.getAllMonopatines();
        } catch (FeignException e) {
            System.err.println("Feign Error al obtener monopatines: " + e.status() + " | Mensaje: " + e.getMessage());
            throw new RuntimeException("Error de comunicación con MS-Monopatín (Código: " + e.status() + ").", e);
        } catch (Exception e) {
            System.err.println("Error de conexión al obtener monopatines: " + e.getMessage());
            throw new RuntimeException("Fallo al conectar con MS-Monopatín.", e);
        }

        List<MonopatinDTO> monopatines = response.getBody();
        if (monopatines == null || !response.getStatusCode().is2xxSuccessful()) {
            return new ArrayList<>();
        }

        List<ReporteMonopatinXKm> reportes = new ArrayList<>();

        for (MonopatinDTO monopatin : monopatines) {


            // 1. Obtener métricas NETAS desde la entidad Monopatín
            Long tiempoUsoNetoSegundos = monopatin.getTiempoUso();
            Long kmRecorridos = (long) monopatin.getKmRecorridos();

            // 2. Calcular tiempo total de pausa (Necesario para el reporte 'con pausas')
            long tiempoTotalPausaSegundos = 0;
            if (pausas) {

                List<ViajeDTO> viajesDelMonopatin = viajeFeingClient.getViajesByMonopatinId(monopatin.getId());
                if (viajesDelMonopatin != null) {
                    for (ViajeDTO viaje : viajesDelMonopatin) {
                        if (viaje.getFin() != null) {
                            try {
                                Long pausaSegundosViaje = viajeFeingClient.getTiempoTotalPausaSegundos(viaje.getId());
                                if (pausaSegundosViaje != null) {
                                    tiempoTotalPausaSegundos += pausaSegundosViaje;
                                }
                            } catch (Exception e) {
                                System.err.println("Advertencia: No se pudo obtener pausa del viaje " + viaje.getId() + ". Continuando...");
                            }
                        }
                    }
                }
            }


            // 3. Determinar el valor FINAL a reportar (Neto o Total)
            Long tiempoReporte;
            if (pausas) {
                // Si 'pausas' es true, reportamos Tiempo NETO + Tiempo PAUSAS
                tiempoReporte = tiempoUsoNetoSegundos + tiempoTotalPausaSegundos;
            } else {
                // Si 'pausas' es false, reportamos solo Tiempo NETO
                tiempoReporte = tiempoUsoNetoSegundos;
            }

            // 4. Crear el DTO de reporte utilizando el ReporteMonopatinXKm base
            ReporteMonopatinXKm entrada = new ReporteMonopatinXKm(
                    monopatin.getId(),
                    kmRecorridos,
                    tiempoReporte // Este valor es el que cambia según 'pausas'
            );
            reportes.add(entrada);

        }

        return reportes;
    }

    /**
     * PUNTO B
     * Anula una cuenta de usuario, inhabilitando su uso.
     * @param idCuenta ID de la cuenta a anular.
     * @return El DTO de la Cuenta actualizada.
     */
    public void anularCuentaUsuario(String idCuenta) {

        ResponseEntity<Void> response = cuentaFeingClient.anularCuenta(idCuenta);

        // Si Cuenta devuelve 204 (No Content) o 200, es exitoso.
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error al anular la cuenta " + idCuenta + " en el MS Cuenta. Código de estado: " + response.getStatusCodeValue());
        }


    }

    /**
     * PUNTO C
     * Consulta los monopatines con más de X viajes en un cierto año
     * @param minViajes Mínimo de viajes (X).
     * @param year Año de filtrado.
     * @return Lista de Monopatines que superan la cantidad de viajes.
     */
    public List<ReporteMonopatinContadorViajes> getMonopatinesConMasDeXViajes(int minViajes, int year) {

        // Obtener de Viaje el conteo agregado de todos los monopatines en ese año
        List<ReporteMonopatinContadorViajes> todosLosMonopatines = viajeFeingClient.getMonopatinesPorViajes(year);

        // Filtra la lista localmente para obtener solo aquellos con más de X viajes
        return todosLosMonopatines.stream()
                .filter(m -> m.getCantidadViajes() >= minViajes)
                .toList();
    }

    /**
     * PUNTO D
     */
    public Double obtenerTotalFacturado(int anio, int mesInicio, int mesFin) {
        return facturacionFeingClient.getTotalFacturado(anio, mesInicio, mesFin).getBody();
    }

    /**
     * Punto E
     */

    public ReporteUsoDTO getUsuariosQueMasUsanMonopatines(String rol, LocalDate inicio, LocalDate fin) {
        // Obtener todos los usuarios con ese rol
        List<UsuarioUsoDTO> usuarios = usuarioFeingClient.getUsuarios();

        List<String> userIds = usuarios.stream()
                .filter(u -> u.getRol().equalsIgnoreCase(rol))
                .map(UsuarioUsoDTO::getId)
                .toList();

        // Llamar al MS de viajes para obtener el uso de esos usuarios
        return viajeFeingClient.getReporteUso(userIds, inicio, fin);
    }

    /**
     * PUNTO F
     * @param tarifaDTO
     * @param fechaActivacion
     */
    public void ajustarTarifas(TarifaDTO tarifaDTO,LocalDate fechaActivacion) {
        tarifaDTO.setFechaActivacion(fechaActivacion);
        tarifaFeingClient.actualizarTarifas(tarifaDTO, fechaActivacion);
    }
}
