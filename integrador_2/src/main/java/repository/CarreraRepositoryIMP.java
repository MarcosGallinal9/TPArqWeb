package repository;

import com.opencsv.CSVReader;
import dto.CarreraDTO;
import dto.CarreraReporteDTO;
import dto.EstudianteDTO;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Carrera;

import java.io.FileReader;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
                                                SELECT c.carrera, COUNT(ec.estudiante) AS cantidadInscriptos 
                                                FROM Carrera c 
                                                JOIN EstudianteCarrera ec 
                                                GROUP BY c.carrera 
                                                HAVING COUNT(ec.estudiante) > 0 
                                                ORDER BY cantidadInscriptos DESC  """, CarreraDTO.class).getResultList();
    em.close();
    return carreras;
}

@Override
public List<String> generarReporte() {
    EntityManager em = JPAUtil.getEntityManager();

    // Inscriptos por año
    List<CarreraReporteDTO> inscriptos = em.createQuery(
            "SELECT new dto.CarreraReporteDTO(c.carrera, ec.inscripcion, COUNT(ec)) " +
                    "FROM Carrera c JOIN c.estudiantesCarrera ec " +
                    "GROUP BY c.carrera, ec.inscripcion " +
                    "ORDER BY c.carrera ASC, ec.inscripcion ASC",
            CarreraReporteDTO.class
    ).getResultList();

    // Graduados por año
    List<CarreraReporteDTO> graduados = em.createQuery(
            "SELECT new dto.CarreraReporteDTO(c.carrera, ec.graduacion, COUNT(ec)) " +
                    "FROM Carrera c JOIN c.estudiantesCarrera ec " +
                    "WHERE ec.graduacion IS NOT NULL " +
                    "GROUP BY c.carrera, ec.graduacion " +
                    "ORDER BY c.carrera ASC, ec.graduacion ASC",
            CarreraReporteDTO.class
    ).getResultList();

    em.close();


    Map<String, Map<Year, CarreraReporteDTO>> reporteMap = new TreeMap<>();

    // Procesar inscriptos
    for (CarreraReporteDTO dto : inscriptos) {
        reporteMap
                .computeIfAbsent(dto.getCarrera(), k -> new TreeMap<>())
                .computeIfAbsent(dto.getAnio(), k -> new CarreraReporteDTO(dto.getCarrera(), dto.getAnio()))
                .setInscriptos(dto.getInscriptos()); // usa el valor devuelto en la query
    }

    // Procesar graduados
    for (CarreraReporteDTO dto : graduados) {
        reporteMap
                .computeIfAbsent(dto.getCarrera(), k -> new TreeMap<>())
                .computeIfAbsent(dto.getAnio(), k -> new CarreraReporteDTO(dto.getCarrera(), dto.getAnio()))
                .setGraduados(dto.getInscriptos());
    }

    // Generar salida
    List<String> salida = new ArrayList<>();
    for (String carrera : reporteMap.keySet()) {
        salida.add("Carrera: " + carrera);
        for (CarreraReporteDTO fila : reporteMap.get(carrera).values()) {
            salida.add("\tAño: " + fila.getAnio());
            salida.add("\t\tInscriptos: " + fila.getInscriptos());
            salida.add("\t\tGraduados: " + fila.getGraduados());
        }
        salida.add("");
    }


    salida.forEach(System.out::println);

    return salida;
}
}
