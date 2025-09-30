package org.example;

import dto.CarreraDTO;
import dto.EstudianteDTO;
import factory.JPAUtil;
import jakarta.persistence.EntityManager;
import modelo.Carrera;
import modelo.Estudiante;
import repository.CarreraRepositoryIMP;
import repository.EstudianteCarreraRepositoryIMP;
import repository.EstudianteRepositoryIMP;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        CarreraRepositoryIMP carreraRepositoryIMP = new CarreraRepositoryIMP();
        EstudianteRepositoryIMP estudianteRepositoryIMP = new EstudianteRepositoryIMP();
        EstudianteCarreraRepositoryIMP estudianteCarreraRepositoryIMP = new EstudianteCarreraRepositoryIMP();

        //Cargar datos desde archivos CSV
        carreraRepositoryIMP.insertarCSV("main/resources/carreras.csv");
        estudianteRepositoryIMP.insertarCSV("main/resources/estudiantes.csv");
        estudianteCarreraRepositoryIMP.insertarCSV("main/resources/estudianteCarrera.csv");

        //Dar de alta un estudiante
        System.out.println("\n== DANDO DE ALTA UN ESTUDIANTE ==");
        estudianteRepositoryIMP.darAltaEstudiante(45000111, "Juan", "Pérez", 22, "Male", "La Plata", 12345);

        //Matricular estudiante en una carrera
        System.out.println("\n== MATRICULANDO ESTUDIANTE EN CARRERA ==");
        EntityManager em = JPAUtil.getEntityManager();
        Estudiante estudiante = em.find(Estudiante.class, 1); // id de estudiante
        Carrera carrera = em.find(Carrera.class, 1); // id de carrera
        estudianteCarreraRepositoryIMP.matricularEstudiante(estudiante, carrera);
        em.close();

        //Recuperar todos los estudiantes ordenados
        System.out.println("\n== LISTA DE ESTUDIANTES ORDENADOS ==");
        List<EstudianteDTO> estudiantesOrdenados = estudianteRepositoryIMP.obtenerEstudiantesOrdenados();
        estudiantesOrdenados.forEach(System.out::println);

        //Buscar estudiante por libreta universitaria
        System.out.println("\n== BUSCAR ESTUDIANTE POR LU ==");
        EstudianteDTO estudianteLU = estudianteRepositoryIMP.estudiantePorLU(10705);
        System.out.println(estudianteLU);

        //Recuperar estudiantes por genero
        System.out.println("\n== ESTUDIANTES POR GÉNERO FEMENINO ==");
        List<EstudianteDTO> estudiantesF = estudianteRepositoryIMP.obtenerEstudiantesXGenero("Female");
        estudiantesF.forEach(System.out::println);

        //Recuperar carreras con inscriptos ordenadas
        System.out.println("\n== CARRERAS CON ESTUDIANTES ORDENADAS ==");
        List<CarreraDTO> carrerasConInscriptos = carreraRepositoryIMP.carrerasConEstudiantesOrdenadas();
        carrerasConInscriptos.forEach(System.out::println);

        //Estudiantes de una carrera en una ciudad
        System.out.println("\n== ESTUDIANTES DE MEDICINA EN SANTIAGO ==");
        List<EstudianteDTO> estCarreraCiudad = estudianteRepositoryIMP.listarEstudiantes("Medicina", "Santiago");
        estCarreraCiudad.forEach(System.out::println);

        //Reporte de carreras
        System.out.println("\n== REPORTE DE CARRERAS ==");
        List<String> reporte = carreraRepositoryIMP.generarReporte();
        reporte.forEach(System.out::println);



    }
}