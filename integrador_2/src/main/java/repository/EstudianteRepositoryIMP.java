package repository;

import com.opencsv.CSVReader;
import dto.EstudianteDTO;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Estudiante;

import java.io.FileReader;
import java.util.List;

public class EstudianteRepositoryIMP implements EstudianteRepository{

    @Override
    public void insertarCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(getClass().getClassLoader().getResource(rutaArchivo).getFile()))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Estudiante estudiante  = new Estudiante();
                estudiante.setDni(Integer.parseInt(linea[0]));
                estudiante.setNombre(linea[1]);
                estudiante.setApellido(linea[2]);
                estudiante.setEdad(Integer.parseInt(linea[3]));
                estudiante.setGenero(linea[4]);
                estudiante.setCiudadResidencia(linea[5]);
                estudiante.setNroLibreta(Integer.parseInt(linea[6]));

                em.merge(estudiante);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            em.close();
        }

    }

    @Override
    public EstudianteDTO darAltaEstudiante(int dni, String nombre, String apellido, int edad, String genero, String ciudad, int LU) {
        EntityManager em = JPAUtil.getEntityManager();
        Estudiante estudiante = new Estudiante();

            estudiante.setDni(dni);
            estudiante.setNombre(nombre);
            estudiante.setApellido(apellido);
            estudiante.setEdad(edad);
            estudiante.setGenero(genero);
            estudiante.setCiudadResidencia(ciudad);
            estudiante.setNroLibreta(LU);

        try {
            em.getTransaction().begin();
            Estudiante existente = em.find(Estudiante.class, dni);
            if (existente == null) {
                em.persist(estudiante);
            } else {
                existente.setNombre(nombre);
                existente.setApellido(apellido);
                existente.setEdad(edad);
                existente.setGenero(genero);
                existente.setCiudadResidencia(ciudad);
                existente.setNroLibreta(LU);
                em.merge(existente);
                estudiante = existente;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return new EstudianteDTO(
                estudiante.getDni(),
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getEdad(),
                estudiante.getGenero(),
                estudiante.getCiudadResidencia(),
                estudiante.getNroLibreta()
        );

    }

    @Override
    public List<EstudianteDTO> obtenerEstudiantesOrdenados(){
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantes = em.createQuery("""
                                SELECT new dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudadResidencia, e.nroLibreta)
                                FROM Estudiante e ORDER BY e.edad DESC""",EstudianteDTO.class).getResultList();
        em.close();
        return estudiantes;
    }

    @Override
    public EstudianteDTO estudiantePorLU(int libretaUniversitaria){
        EntityManager em = JPAUtil.getEntityManager();
        EstudianteDTO estudiante = em.createQuery("""
                                SELECT new dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudadResidencia, e.nroLibreta)
                                FROM Estudiante e 
                                WHERE e.nroLibreta = :libretaUniversitaria""", EstudianteDTO.class)
                .setParameter("libretaUniversitaria", libretaUniversitaria)
                .getSingleResult();

        em.close();
        return estudiante;
    }

    @Override
    public List<EstudianteDTO> obtenerEstudiantesXGenero(String genero) {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantes = em.createQuery("""
                                SELECT new dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudadResidencia, e.nroLibreta)
                                FROM Estudiante e 
                                WHERE e.genero = :genero""", EstudianteDTO.class)
                .setParameter("genero", genero)
                .getResultList();
        em.close();
        return estudiantes;
    }

    @Override
    public List<EstudianteDTO> listarEstudiantes(String carrera , String ciudad){
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantes = em.createQuery("""
                                SELECT DISTINCT new dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudadResidencia, e.nroLibreta)
                                FROM Estudiante e
                                JOIN e.carreras ec
                                JOIN ec.carrera c
                                WHERE e.ciudadResidencia = :ciudad AND c.carrera = :carrera""", EstudianteDTO.class)
                .setParameter("ciudad", ciudad)
                .setParameter("carrera", carrera)
                .getResultList();
        em.close();
        return estudiantes;
    }
}

