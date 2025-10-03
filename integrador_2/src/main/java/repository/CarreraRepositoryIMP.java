package repository;

import com.opencsv.CSVReader;
import dto.CarreraInscriptosDTO;
import dto.CarreraReporteDTO;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Carrera;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CarreraRepositoryIMP implements CarreraRepository {

    @Override
    public void insertarCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(getClass().getClassLoader().getResource(rutaArchivo).getFile()))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Carrera carrera = new Carrera();
                if (linea.length >= 3) {
                    if (linea[0] != null && !linea[0].isBlank()) {
                        try { carrera.setId_carrera(Integer.parseInt(linea[0])); } catch (Exception ignored) {}
                    }
                    carrera.setCarrera(linea[1]);
                    carrera.setDuracion(Integer.parseInt(linea[2]));
                }
                em.merge(carrera);
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
    public List<CarreraInscriptosDTO> carrerasConEstudiantesOrdenadas(){
        EntityManager em = JPAUtil.getEntityManager();
        List<CarreraInscriptosDTO> carreras = em.createQuery("""
                                SELECT new dto.CarreraInscriptosDTO(c.carrera, COUNT(ec))
                                FROM Carrera c JOIN c.estudiantesCarrera ec
                                GROUP BY c.carrera
                                HAVING COUNT(ec) > 0
                                ORDER BY COUNT(ec) DESC""", CarreraInscriptosDTO.class).getResultList();
        em.close();
        return carreras;
    }

    @Override
    public List<String> generarReporte() {
        EntityManager em = JPAUtil.getEntityManager();

        // Inscriptos por año
        List<CarreraReporteDTO> inscriptos = em.createQuery("""
                        SELECT new dto.CarreraReporteDTO(c.carrera, ec.inscripcion, COUNT(ec))
                        FROM Carrera c JOIN c.estudiantesCarrera ec
                        GROUP BY c.carrera, ec.inscripcion
                        ORDER BY c.carrera ASC, ec.inscripcion ASC """ , CarreraReporteDTO.class).getResultList();

        // Graduados por año
        List<CarreraReporteDTO> graduados = em.createQuery("""
                        SELECT new dto.CarreraReporteDTO(c.carrera, ec.graduacion, COUNT(ec))
                        FROM Carrera c JOIN c.estudiantesCarrera ec
                        WHERE ec.graduacion IS NOT NULL AND ec.graduacion <> 0
                        GROUP BY c.carrera, ec.graduacion
                        ORDER BY c.carrera ASC, ec.graduacion ASC""", CarreraReporteDTO.class).getResultList();

        em.close();

        Map<String, Map<Integer, CarreraReporteDTO>> reporteMap = new TreeMap<>();

        // Procesar inscriptos
        for (CarreraReporteDTO dto : inscriptos) {
            reporteMap
                    .computeIfAbsent(dto.getCarrera(), k -> new TreeMap<>())
                    .computeIfAbsent(dto.getAnio(), k -> new CarreraReporteDTO(dto.getCarrera(), dto.getAnio(), 0))
                    .setInscriptos(dto.getInscriptos());
        }

        // Procesar graduados
        for (CarreraReporteDTO dto : graduados) {
            reporteMap
                    .computeIfAbsent(dto.getCarrera(), k -> new TreeMap<>())
                    .computeIfAbsent(dto.getAnio(), k -> new CarreraReporteDTO(dto.getCarrera(), dto.getAnio(), 0))
                    .setGraduados(dto.getInscriptos()); // dto.getInscriptos() contiene la cuenta retornada
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
