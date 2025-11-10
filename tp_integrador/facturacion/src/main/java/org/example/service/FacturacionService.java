package org.example.service;

import org.example.entity.Facturacion;
import org.example.repository.FacturacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
public class FacturacionService {

    FacturacionRepository facturacionRepository;

    public FacturacionService(FacturacionRepository facturacionRepository) {
        this.facturacionRepository = facturacionRepository;
    }

    public List<Facturacion> getAll(){

        return facturacionRepository.findAll();
    }

    public Facturacion save(Facturacion factura){
        Facturacion facturaNew;
        facturaNew = facturacionRepository.save(factura);
        return facturaNew;
    }
    public void delete(Facturacion factura){
        facturacionRepository.delete(factura);
    }

    public Facturacion findById(String id){
        return facturacionRepository.findById(id).orElse(null);
    }

    public Facturacion update(Facturacion factura){
        return facturacionRepository.save(factura);
    }

    public List<Facturacion> byUserId(String userId){
        return facturacionRepository.findByUsuarioId(userId);
    }

    public double  obtenerTotalFacturado(int anio, int mesInicio, int mesFin) {
        LocalDate fechaInicio = LocalDate.of(anio, mesInicio, 1);
        LocalDate fechaFin = LocalDate.of(anio, mesFin, YearMonth.of(anio, mesFin).lengthOfMonth());

        Date inicio = java.sql.Date.valueOf(fechaInicio);
        Date fin = java.sql.Date.valueOf(fechaFin);

        return facturacionRepository.findByFechaBetween(inicio, fin);
    }
}
