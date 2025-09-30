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

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Estudiante estudiante  = new Estudiante();
                estudiante.setNombre(linea[1]);
                estudiante.setApellido(linea[2]);
                estudiante.setEdad(Integer.parseInt(linea[3]));
                estudiante.setGenero(linea[4]);
                estudiante.setCiudad(linea[5]);
                estudiante.setLU(Integer.parseInt(linea[6]));

                em.persist(estudiante);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    @Override
    public void darAltaEstudiante(int dni, String nombre, String apellido, int edad, String genero, String ciudad, int LU) {
        EntityManager em = JPAUtil.getEntityManager();
        em.createQuery(
                        "INSERT INTO Estudiante (dni, nombre, apellido, edad, genero , ciudad , LU) VALUES (:dni, :nombre, :apellido, :edad, :genero, :ciudad, :LU)")
                .setParameter("dni", dni)
                .setParameter("nombre", nombre)
                .setParameter("apellido", apellido)
                .setParameter("edad", edad)
                .setParameter("genero", genero)
                .setParameter("ciudad", ciudad)
                .setParameter("LU", LU)
                .executeUpdate();

        em.close();
    }

    @Override //Se obtienen estudiantes ordenados por edad
    public List<EstudianteDTO> obtenerEstudiantesOrdenados(){
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantes = em.createQuery("SELECT e FROM Estudiante e ORDER BY edad DESC ", EstudianteDTO.class).getResultList();
        em.close();
        return estudiantes;
    }

    @Override
    public EstudianteDTO estudiantePorLU(int libretaUniversitaria){
        EntityManager em = JPAUtil.getEntityManager();
        EstudianteDTO estudiante = em.createQuery(
                        "SELECT e  FROM Estudiante e WHERE e.LU = :libretaUniversitaria", EstudianteDTO.class)
                .setParameter("LU", libretaUniversitaria)
                .getSingleResult();

        em.close();
        return estudiante;
    }

    @Override
    public List<EstudianteDTO> obtenerEstudiantesXGenero(String genero) {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantes = em.createQuery("SELECT e FROM Estudiante e WHERE e.genero = :genero ", EstudianteDTO.class)
                .getResultList();
        em.close();
        return estudiantes;
    }

    @Override
    public List<EstudianteDTO> listarEstudiantes(String carrera , String ciudad){
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantes = em.createQuery("SELECT e FROM Estudiante e JOIN EstudianteCarrera ec JOIN Carrera  c WHERE e.ciudad = :ciudad AND c.carrera = :carrera", EstudianteDTO.class)
                .getResultList();
        em.close();
        return estudiantes;
    }
}
