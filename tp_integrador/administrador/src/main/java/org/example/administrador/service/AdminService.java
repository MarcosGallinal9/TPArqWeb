package org.example.administrador.service;

import org.example.administrador.dto.MonopatinDTO;
import org.example.administrador.dto.ReporteMonopatinXKm;
import org.example.administrador.entity.Admin;
import org.example.administrador.feingClients.MonopatinFeingClient;
import org.example.administrador.repository.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.SequencedCollection;

@Service
public class AdminService {
    //Feing clients
    MonopatinFeingClient monopatinFeingClient;
    AdminRepository adminRepository;
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public List<ReporteMonopatinXKm> getReportes() {
        List<ReporteMonopatinXKm> reporteMonopatinXKm = new ArrayList<>();
        ResponseEntity<List<MonopatinDTO>> monopatines= monopatinFeingClient.getAllMonopatines();
        String id;
        float kmRecorridos;
        Long tiempoDeUsoNeto;
        Long tiempoDeUsoTotal;
        for(int i=0;i<monopatines.getBody().size();i++){
            id = monopatines.getBody().get(i).getId();
            kmRecorridos= monopatines.getBody().get(i).getKmRecorridos();
            tiempoDeUsoNeto= monopatines.getBody().get(i).getTiempoUso();
            tiempoDeUsoTotal= monopatines.getBody().get(i).
        }
    }
}
