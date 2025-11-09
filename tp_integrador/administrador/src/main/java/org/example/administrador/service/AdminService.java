package org.example.administrador.service;

import org.example.administrador.dto.*;
import org.example.administrador.entity.Admin;
import org.example.administrador.feingClients.CuentaFeingClient;
import org.example.administrador.feingClients.MonopatinFeingClient;
import org.example.administrador.feingClients.ViajeFeingClient;
import org.example.administrador.repository.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    //Feing clients
    MonopatinFeingClient monopatinFeingClient;
    ViajeFeingClient viajeFeingClient;
    CuentaFeingClient cuentaFeingClient;

    AdminRepository adminRepository;

    public AdminService(MonopatinFeingClient monopatinFeingClient, ViajeFeingClient viajeFeingClient, CuentaFeingClient cuentaFeingClient, AdminRepository adminRepository) {
        this.monopatinFeingClient = monopatinFeingClient;
        this.viajeFeingClient = viajeFeingClient;
        this.cuentaFeingClient = cuentaFeingClient;
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
    public List<ReporteMonopatinXKm> generarReporteUso() {

        ResponseEntity<List<MonopatinDTO>> response = monopatinFeingClient.getAllMonopatines();

        List<MonopatinDTO> monopatines = response.getBody();
        List<ReporteMonopatinXKm> reportes = new ArrayList<>();

        for (MonopatinDTO monopatin : monopatines) {

            // Obtener métricas NETAS desde la entidad Monopatín
            Long tiempoUsoNetoSegundos = monopatin.getTiempoUsoSegundos();
            float kmRecorridosFloat = monopatin.getKmRecorridos();
            Long kmRecorridos = (long) kmRecorridosFloat;

            //Obtener el listado de VIAJES del Monopatín (MS Viaje)
            List<ViajeDTO> viajesDelMonopatin = viajeFeingClient.getViajesByMonopatinId(monopatin.getId());

            long tiempoTotalPausaSegundos = 0;

            //Iterar sobre los viajes para sumar el tiempo de pausa
            for (ViajeDTO viaje : viajesDelMonopatin) {
                // Solo consulta pausas para viajes finalizados
                if (viaje.getFin() != null) {
                    try {
                        // Obtener la suma de pausas de ESTE viaje
                        Long pausaSegundosViaje = viajeFeingClient.getTiempoTotalPausaSegundos(viaje.getId());
                        tiempoTotalPausaSegundos += pausaSegundosViaje;
                    } catch (Exception e) {
                        System.err.println("Error al obtener pausa del viaje " + viaje.getId() + ": " + e.getMessage());
                    }
                }
            }

            // Calcular el tiempo TOTAL (incluyendo pausas)
            Long tiempoDeUsoTotalSegundos = tiempoUsoNetoSegundos + tiempoTotalPausaSegundos;

            ReporteMonopatinXKm entrada = new ReporteMonopatinXKm(
                    monopatin.getId(),
                    kmRecorridos,
                    tiempoUsoNetoSegundos,
                    tiempoDeUsoTotalSegundos
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
                .filter(m -> m.getCantidadViajes() > minViajes)
                .toList();
    }
}
