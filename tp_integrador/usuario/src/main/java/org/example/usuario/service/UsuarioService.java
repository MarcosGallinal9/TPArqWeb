package org.example.usuario.service;

import org.example.usuario.dto.cuentaDto;
import org.example.usuario.entity.Usuario;
import org.example.usuario.dto.monopatinDto;
import org.example.usuario.feignClients.monopatinFeignClient;
import org.example.usuario.repository.UsuarioRepository;
import org.example.usuario.feignClients.cuentaFeignClient;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;
    cuentaFeignClient cuentaFeignClient;
    monopatinFeignClient monopatinFeignClient;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    public Usuario findById(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario regristrarUsuario(Usuario usuario, String nroCuenta) {
        cuentaDto cuentaAsociar =  cuentaFeignClient.getCuenta(nroCuenta);
        if (cuentaAsociar == null) {
            throw new RuntimeException();
        }
        usuario.getCuentas().add(nroCuenta);
        return usuarioRepository.save(usuario);
    }

    public List<monopatinDto> buscarMonopatinesCercanos(double lat, double lng, double radiokm) {
        return monopatinFeignClient.getMonopatinesCercanos(lat, lng, radiokm);
    }




}
