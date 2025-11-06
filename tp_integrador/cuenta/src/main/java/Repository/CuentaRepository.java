package Repository;

import entity.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends MongoRepository<Cuenta,Long>{
    List<Integer> findByUserId(Long userId);
}
