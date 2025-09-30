package repository;

import com.opencsv.CSVReader;
import dto.CarreraDTO;
import dto.EstudianteDTO;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Carrera;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CarreraRepositoryIMP implements  CarreraRepository {

    @Override
    public void insertarCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Carrera carrera = new Carrera();
                carrera.setCarrera(linea[1]);
                carrera.setDuracion(Integer.parseInt(linea[2]));


                em.persist(carrera);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

@Override
public List<CarreraDTO> carrerasConEstudiantesOrdenadas(){
    EntityManager em = JPAUtil.getEntityManager();
    List<CarreraDTO> carreras = em.createQuery("""
                                                SELECT c.carrera, COUNT(ec.id_estudiante) AS cantidadInscriptos 
                                                FROM Carrera c 
                                                JOIN EstudianteCarrera ec 
                                                GROUP BY c.carrera 
                                                HAVING COUNT(ec.id_estudiante) > 0 
                                                ORDER BY cantidadInscriptos DESC  """, CarreraDTO.class).getResultList();
    em.close();
    return carreras;
}

@Override
public List<String> generarReporte(){
    EntityManager em = JPAUtil.getEntityManager();

    // Query de inscriptos
    List<CarreraDTO> carrerasConInscriptos = em.createQuery(
            "SELECT new dto.CarreraDTO(c.carrera, ec.inscripcion, COUNT(ec)) " +
                    "FROM Carrera c JOIN c.estudiantesCarrera ec " +
                    "GROUP BY c.carrera, ec.inscripcion " +
                    "ORDER BY c.carrera ASC, ec.inscripcion ASC",
            CarreraDTO.class
    ).getResultList();

    // Query de graduados
    List<CarreraDTO> carrerasConGraduados = em.createQuery(
            "SELECT new java.dto.CarreraDTO(c.carrera, ec.graduacion, COUNT(ec)) " +
                    "FROM Carrera c JOIN c.EstudianteCarrera ec " +
                    "WHERE ec.graduacion IS NOT NULL " +
                    "GROUP BY c.carrera, ec.graduacion " +
                    "ORDER BY c.carrera ASC, ec.graduacion ASC",
            CarreraDTO.class
    ).getResultList();

    em.close();

    // Lista de strings con el reporte
    List<String> generarReporte = new ArrayList<>();

    // Inscriptos
    for (CarreraDTO dto : carrerasConInscriptos) {
        generarReporte.add(
                "Carrera: " + dto.getCarrera() +
                        " | Año de inscripción: " + dto.getAnio() +
                        " | Inscriptos: " + dto.getCantidad()
        );
    }

    // Graduados
    for (CarreraDTO dto : carrerasConGraduados) {
        generarReporte.add(
                "Carrera: " + dto.getCarrera() +
                        " | Año de graduación: " + dto.getAnio() +
                        " | Graduados: " + dto.getCantidad()
        );
    }

    // Imprimir en consola
    generarReporte.forEach(System.out::println);

    return generarReporte;
}
}
