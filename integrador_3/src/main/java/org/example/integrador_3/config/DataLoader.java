package org.example.integrador_3.config;


import jakarta.annotation.PostConstruct;
import org.example.integrador_3.entity.Carrera;
import org.example.integrador_3.entity.Estudiante;
import org.example.integrador_3.entity.EstudianteCarrera;
import org.example.integrador_3.repository.CarreraRepository;
import org.example.integrador_3.repository.EstudianteCarreraRepository;
import org.example.integrador_3.repository.EstudianteRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DataLoader {

    private final CarreraRepository carreraRepository;
    private final EstudianteRepository estudianteRepository;
    private final EstudianteCarreraRepository estudianteCarreraRepository;

    public DataLoader(CarreraRepository carreraRepository,
                      EstudianteRepository estudianteRepository,
                      EstudianteCarreraRepository estudianteCarreraRepository) {
        this.carreraRepository = carreraRepository;
        this.estudianteRepository = estudianteRepository;
        this.estudianteCarreraRepository = estudianteCarreraRepository;
    }

    @PostConstruct
    public void loadData() {
        loadCarreras();
        loadEstudiantes();
        loadEstudianteCarrera();
    }

    private void loadCarreras() {
        try (var reader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("data/carreras.csv").getInputStream(),
                        StandardCharsets.UTF_8))) {

            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] f = line.split(",");
                Carrera c = new Carrera();
                c.setIdCarrera(Integer.parseInt(f[0]));
                c.setCarrera(f[1]);
                c.setDuracion(Integer.parseInt(f[2]));
                carreraRepository.save(c);
            }
            System.out.println("Carreras cargadas correctamente");
        } catch (Exception e) {
            System.err.println("Error al cargar carreras: " + e.getMessage());
        }
    }

    private void loadEstudiantes() {
        try (var reader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("data/estudiantes.csv").getInputStream(),
                        StandardCharsets.UTF_8))) {

            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] f = line.split(",");
                Estudiante e = new Estudiante();
                e.setDni(Integer.parseInt(f[0]));
                e.setNombre(f[1]);
                e.setApellido(f[2]);
                e.setEdad(Integer.parseInt(f[3]));
                e.setGenero(f[4]);
                e.setCiudadResidencia(f[5]);
                e.setNroLibreta(Integer.parseInt(f[6]));
                estudianteRepository.save(e);
            }
            System.out.println("Estudiantes cargados correctamente");
        } catch (Exception e) {
            System.err.println("Error al cargar estudiantes: " + e.getMessage());
        }
    }

    private void loadEstudianteCarrera() {
        try (var reader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("data/estudianteCarrera.csv").getInputStream(),
                        StandardCharsets.UTF_8))) {

            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] f = line.split(",");
                EstudianteCarrera ec = new EstudianteCarrera();

                Estudiante estudiante = estudianteRepository.findById((long) Integer.parseInt(f[1]))
                        .orElseThrow(() -> new RuntimeException("No existe estudiante con dni " + f[1]));
                Carrera carrera = carreraRepository.findById((long) Integer.parseInt(f[2]))
                        .orElseThrow(() -> new RuntimeException("No existe carrera con id " + f[2]));

                ec.setEstudiante(estudiante);
                ec.setCarrera(carrera);
                ec.setInscripcion(Integer.parseInt(f[3]));
                ec.setGraduacion(Integer.parseInt(f[4]));
                ec.setAntiguedad(Integer.parseInt(f[5]));
                estudianteCarreraRepository.save(ec);
            }
            System.out.println("EstudianteCarrera cargados correctamente");
        } catch (Exception e) {
            System.err.println("Error al cargar estudianteCarrera: " + e.getMessage());
        }
    }
}

