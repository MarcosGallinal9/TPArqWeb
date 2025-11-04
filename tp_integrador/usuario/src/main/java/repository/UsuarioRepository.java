package repository;

import entity.Usuario;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    List<Monopatin> findByUserId(Long userId);
}
