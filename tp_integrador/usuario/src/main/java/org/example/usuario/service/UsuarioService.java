package org.example.usuario.service;

import org.example.usuario.entity.Usuario;
import org.example.usuario.repository.UsuarioRepository;
import org.example.usuario.feignClients.cuentaFeignClient;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;
    cuentaFeignClient cuentaFeignClient;

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


}
