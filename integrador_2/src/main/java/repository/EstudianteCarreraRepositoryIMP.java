package repository;

import com.opencsv.CSVReader;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Carrera;
import modelo.Estudiante;
import modelo.EstudianteCarrera;

import java.io.FileReader;

public class EstudianteCarreraRepositoryIMP implements EstudianteCarreraRepository{

    @Override
    public void insertarCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(getClass().getClassLoader().getResource(rutaArchivo).getFile()))) {
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

                // inscripcion/graduacion pueden estar vacíos
                if (linea.length > 3 && linea[3] != null && !linea[3].isBlank()) {
                    estudianteCarrera.setInscripcion(Integer.parseInt(linea[3]));
                } else {
                    estudianteCarrera.setInscripcion(null);
                }

                if (linea.length > 4 && linea[4] != null && !linea[4].isBlank()) {
                    estudianteCarrera.setGraduacion(Integer.parseInt(linea[4]));
                } else {
                    estudianteCarrera.setGraduacion(null);
                }

                if (linea.length > 5 && linea[5] != null && !linea[5].isBlank()) {
                    estudianteCarrera.setAntiguedad(Integer.parseInt(linea[5]));
                } else {
                    estudianteCarrera.setAntiguedad(0);
                }

                em.merge(estudianteCarrera);
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
    public void matricularEstudiante(int dniEstudiante, int idCarrera){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Estudiante estudiante = em.find(Estudiante.class, dniEstudiante);
            Carrera carrera = em.find(Carrera.class, idCarrera);

            if (estudiante == null || carrera == null) {
                throw new IllegalArgumentException("Estudiante o carrera no encontrados para matricular.");
            }

            EstudianteCarrera ec = new EstudianteCarrera();
            ec.setEstudiante(estudiante);
            ec.setCarrera(carrera);
            ec.setInscripcion(java.time.Year.now().getValue()); // año actual
            ec.setAntiguedad(0);
            ec.setGraduacion(null);

            em.persist(ec);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }
}
