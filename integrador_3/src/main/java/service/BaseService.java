package service;

import entity.Carrera;
import entity.Estudiante;

import java.util.List;

public interface BaseService<T>{
    public List<T> findAll()throws Exception;
    public T findById(Long id)throws Exception;
    public T save(T entity)throws  Exception;
    public T update(Long id, T entity)throws Exception;
    public boolean delete(Long id)throws Exception;
    public T add(T entity)throws Exception;
    public T matricular(Estudiante entity, Carrera entity2)throws Exception;
    public List<T> findAllByOrderByEdad() throws Exception;
    public T getByNroLibreta(Integer nroLibreta);
    public List<T> getByGenero(String genero);
    public List<T> getCarrerasConInscriptosOrdenadas();

}
