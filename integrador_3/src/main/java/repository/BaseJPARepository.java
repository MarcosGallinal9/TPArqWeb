package repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@NoRepositoryBean
public interface BaseJPARepository<T,ID extends Serializable> extends org.springframework.data.repository.Repository<T,ID> {
    void delete(T deleted);
    List<T> findAll();
    Optional<T> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
    T save(T entity); //Dar de alta estudiante, matricular estudiante en carrera
    List<T> findAllByOrderByEdad(); //estudiantes con criterio de ordenamiento simple
    List<T> getByGenero(String genero);
    T getByNroLibreta(Integer nroLibreta);
    List<T> getCarrerasConInscriptosOrdenadas();
    List<T> getByCarrera (String ciudad);
    List<T> getReporte();
    T matricular(T entity, T entity2);



}
