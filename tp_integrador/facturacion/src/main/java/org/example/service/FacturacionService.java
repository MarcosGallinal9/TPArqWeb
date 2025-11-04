package org.example.service;

import org.example.entity.Facturacion;
import org.example.repository.FacturacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturacionService {
    @Autowired
    FacturacionRepository facturacionRepository;

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

    public Facturacion findById(Long id){
        return facturacionRepository.findById(id).orElse(null);
    }

    public Facturacion update(Facturacion factura){
        return facturacionRepository.save(factura);
    }

    public List<Facturacion> byUserId(Long userId){
        return facturacionRepository.findByUserId(userId);
    }
}