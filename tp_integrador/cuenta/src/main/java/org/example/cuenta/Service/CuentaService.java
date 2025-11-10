package org.example.cuenta.Service;

import org.example.cuenta.Repository.CuentaRepository;
import org.example.cuenta.entity.Cuenta;

import java.util.List;



import org.springframework.stereotype.Service;


@Service
public class CuentaService {

    CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public List<Cuenta> getAll(){
        return cuentaRepository.findAll();
    }

    public Cuenta save(Cuenta cuenta){
        Cuenta nuevaCuenta;
        nuevaCuenta = cuentaRepository.save(cuenta);
        return nuevaCuenta;
    }
    public void delete(Cuenta cuenta){
        cuentaRepository.delete(cuenta);
    }

    public Cuenta getByUserId(String id){
        return cuentaRepository.findById(id).orElse(null);
    }

    public Cuenta update(Cuenta cuenta){
        return cuentaRepository.save(cuenta);
    }

    public Cuenta findById(String id){
        return cuentaRepository.findById(id).orElse(null);
    }

    public Cuenta cargarSaldo(String id, double saldo){
        Cuenta cuenta = cuentaRepository.findById(id).orElse(null);
        if (cuenta == null) {
            return null;
        }
        double montoActual = cuenta.getMonto();
        cuenta.setMonto(montoActual + saldo);
        return cuentaRepository.save(cuenta);
    }

    public List<String> getUsuariosAsociados(String id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElse(null);
        return cuenta.getUsuarios();
    }

    /**
     * Busca la cuenta a la que pertenece el usuario.
     * Si un usuario puede tener varias cuentas, retorna la lista.
     */
    public List<Cuenta> getCuentasByUserId(String userId){
        return cuentaRepository.findByUsuariosContaining(userId);
    }
}
