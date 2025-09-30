package org.example;

import repository.CarreraRepositoryIMP;
import repository.EstudianteCarreraRepositoryIMP;
import repository.EstudianteRepositoryIMP;


public class Main {
    public static void main(String[] args) {
        CarreraRepositoryIMP carreraRepositoryIMP = new CarreraRepositoryIMP();
        EstudianteRepositoryIMP estudianteRepositoryIMP = new EstudianteRepositoryIMP();
        EstudianteCarreraRepositoryIMP estudianteCarreraRepositoryIMP = new EstudianteCarreraRepositoryIMP();

        //Cargar datos desde archivos CSV
        carreraRepositoryIMP.insertarCSV("main/resources/carreras.csv");
        estudianteRepositoryIMP.insertarCSV("main/resources/estudiantes.csv");
        estudianteCarreraRepositoryIMP.insertarCSV("main/resources/estudianteCarrera.csv");

    }
}