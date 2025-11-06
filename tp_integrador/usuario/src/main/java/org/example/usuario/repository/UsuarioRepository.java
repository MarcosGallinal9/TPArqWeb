package org.example.usuario.repository;

import org.example.usuario.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario,Long>{
    List<Integer> findByUserId(Long userId);
}
