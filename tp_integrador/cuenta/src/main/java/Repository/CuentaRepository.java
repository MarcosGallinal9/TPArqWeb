package Repository;

import entity.Cuenta;
import entity.Usuario;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Usuario,Long>{
    List<Cuenta> findByUserId(Long userId);
}
