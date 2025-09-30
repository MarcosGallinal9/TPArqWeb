package repository;

import com.opencsv.CSVReader;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Carrera;
import modelo.Estudiante;
import modelo.EstudianteCarrera;

import java.io.FileReader;
import java.time.Year;

public class EstudianteCarreraRepositoryIMP implements EstudianteCarreraRepository{

    @Override
    public void insertarCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                EstudianteCarrera estudianteCarrera = new EstudianteCarrera();


                int idEstudiante = Integer.parseInt(linea[1]);
                Estudiante estudiante = em.find(Estudiante.class, idEstudiante);
                estudianteCarrera.setEstudiante(estudiante);


                int idCarrera = Integer.parseInt(linea[2]);
                Carrera carrera = em.find(Carrera.class, idCarrera);
                estudianteCarrera.setCarrera(carrera);


                estudianteCarrera.setInscripcion(Year.parse(linea[3]));
                estudianteCarrera.setGraduacion(Year.parse(linea[4]));
                estudianteCarrera.setAntiguedad(Integer.parseInt(linea[5]));

                em.persist(estudianteCarrera);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void matricularEstudiante(Estudiante estudiante, Carrera carrera){
        int id_estudiante = estudiante.getDni();
        int id_carrera = carrera.getId_carrera();
        EntityManager em = JPAUtil.getEntityManager();
        em.createQuery(
                        """
                                   INSERT INTO EstudianteCarrera (estudiante, carrera) VALUES (:id_estudiante, :id_carrera)""")
                .setParameter("id_estudiante", id_estudiante)
                .setParameter("id_carrera", id_carrera)
                .executeUpdate();

        em.close();

    }
}
