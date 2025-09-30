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
public void generarReporte() {
    EntityManager em = JPAUtil.getEntityManager();

    // Consulta de inscriptos
    List<Object[]> inscriptos = em.createQuery(
            "SELECT c.carrera, ec.inscripcion, COUNT(ec) " +
                    "FROM Carrera c JOIN c.estudiantesCarrera ec " +
                    "GROUP BY c.carrera, ec.inscripcion " +
                    "ORDER BY c.carrera ASC, ec.inscripcion ASC"
    ).getResultList();

    // Consulta de graduados
    List<Object[]> graduados = em.createQuery(
            "SELECT c.carrera, ec.graduacion, COUNT(ec) " +
                    "FROM Carrera c JOIN c.estudiantesCarrera ec " +
                    "WHERE ec.graduacion IS NOT NULL " +
                    "GROUP BY c.carrera, ec.graduacion " +
                    "ORDER BY c.carrera ASC, ec.graduacion ASC"
    ).getResultList();

    em.close();

    // Estructura de datos: carrera -> año -> DTO
    Map<String, Map<Integer, CarreraReporteDTO>> reporte = new TreeMap<>();

    // Procesar inscriptos
    for (Object[] fila : inscriptos) {
        String carrera = (String) fila[0];
        int anio = ((Year) fila[1]).getValue();
        long cantidad = (Long) fila[2];

        reporte.putIfAbsent(carrera, new TreeMap<>());
        Map<Integer, CarreraReporteDTO> porAnio = reporte.get(carrera);

        porAnio.putIfAbsent(anio, new CarreraReporteDTO(carrera, anio));
        porAnio.get(anio).setInscriptos(cantidad);
    }

    // Procesar graduados
    for (Object[] fila : graduados) {
        String carrera = (String) fila[0];
        int anio = ((Year) fila[1]).getValue();
        long cantidad = (Long) fila[2];

        reporte.putIfAbsent(carrera, new TreeMap<>());
        Map<Integer, CarreraReporteDTO> porAnio = reporte.get(carrera);

        porAnio.putIfAbsent(anio, new CarreraReporteDTO(carrera, anio));
        porAnio.get(anio).setGraduados(cantidad);
    }

    // Imprimir reporte
    for (String carrera : reporte.keySet()) {
        System.out.println("Carrera: " + carrera);

        for (CarreraReporteDTO dto : reporte.get(carrera).values()) {
            System.out.println("  Año: " + dto.getAnio());
            System.out.println("    Inscriptos: " + dto.getInscriptos());
            System.out.println("    Graduados: " + dto.getGraduados());
        }
        System.out.println(); // línea en blanco entre carreras
    }
}
}
