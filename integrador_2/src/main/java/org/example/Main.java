package org.example;

import dto.EstudianteDTO;
import repository.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CarreraRepositoryIMP carreraRepositoryIMP = new CarreraRepositoryIMP();
        EstudianteRepositoryIMP estudianteRepositoryIMP = new EstudianteRepositoryIMP();
        EstudianteCarreraRepositoryIMP estudianteCarreraRepositoryIMP = new EstudianteCarreraRepositoryIMP();

        // Cargar datos desde archivos CSV (ajusta nombres si tus csv se llaman distinto)
        carreraRepositoryIMP.insertarCSV("carreras.csv");
        estudianteRepositoryIMP.insertarCSV("estudiantes.csv");
        estudianteCarreraRepositoryIMP.insertarCSV("estudianteCarrera.csv");

        // Dar de alta un estudiante
        System.out.println("\n== DANDO DE ALTA UN ESTUDIANTE ==");
        EstudianteDTO nuevoEstudiante = estudianteRepositoryIMP.darAltaEstudiante(45000111, "Juan", "Pérez", 22, "Male", "La Plata", 12345);
        System.out.println("Se ha dado de alta el estudiante: " + nuevoEstudiante);

        // Matricular estudiante en una carrera (usar IDs)
        System.out.println("\n== MATRICULANDO ESTUDIANTE EN CARRERA ==");
        int dni= 45000111;
        int carrera= 1;
        estudianteCarreraRepositoryIMP.matricularEstudiante(dni, carrera);
        System.out.println("El estudiante con dni: "+ dni + " fue matriculado en la carrera con el id:  " + carrera);

        // Recuperar todos los estudiantes ordenados
        System.out.println("\n== LISTA DE ESTUDIANTES ORDENADOS POR EDAD DE FORMA DESCENDIENTE==");
        List<EstudianteDTO> estudiantesOrdenados = estudianteRepositoryIMP.obtenerEstudiantesOrdenados();
        estudiantesOrdenados.forEach(System.out::println);

        // Buscar estudiante por libreta universitaria
        int lu= 44502;
        System.out.println("\n== BUSCAR ESTUDIANTE POR CON LA LU:" + lu + " ==");
        EstudianteDTO estudianteLU = estudianteRepositoryIMP.estudiantePorLU(lu);
        System.out.println(estudianteLU);

        // Recuperar estudiantes por genero
        System.out.println("\n== ESTUDIANTES POR GÉNERO FEMENINO ==");
        List<EstudianteDTO> estudiantesF = estudianteRepositoryIMP.obtenerEstudiantesXGenero("Female");
        estudiantesF.forEach(System.out::println);

        // Recuperar carreras con inscriptos ordenadas
        System.out.println("\n== CARRERAS CON ESTUDIANTES ORDENADAS POR LA CANTIDAD DE ALUMNOS INSCRPTOS DE FORMA DESCENDIENTE ==");
        List<?> carrerasConInscriptos = carreraRepositoryIMP.carrerasConEstudiantesOrdenadas();
        carrerasConInscriptos.forEach(System.out::println);

        // Estudiantes de una carrera en una ciudad
        System.out.println("\n== ESTUDIANTES DE INGENIERIA DE SISTEMAS  EN BAISHA ==");
        List<EstudianteDTO> estCarreraCiudad = estudianteRepositoryIMP.listarEstudiantes("Ingenieria de Sistemas", "Baisha");
        estCarreraCiudad.forEach(System.out::println);

        // Reporte de carreras
        System.out.println("\n== REPORTE DE CARRERAS ==");
        List<String> reporte = carreraRepositoryIMP.generarReporte();
        reporte.forEach(System.out::println);
    }
}
