import DAO.*;
import Utils.*;
import entities.*;
import mysql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Datos de conexión a tu base de datos
        final String url = "jdbc:mysql://localhost:3306/integrador";
        final String user = "root";
        final String password = ""; //Contraseña vacia

        try (Connection cn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión establecida con la BD.");

            // Crear el esquema en la base de datos
            CreadorEsquema creador = new CreadorEsquema();
            creador.crearEsquema(cn);
            System.out.println("Esquema creado correctamente.");

            // Crear DAOs
            ClienteDAO clienteDAO = new MySQLClienteDAO(cn);
            ProductoDAO productoDAO = new MySQLProductoDAO(cn);
            FacturaDAO facturaDAO = new MySQLFacturaDAO(cn);
            Factura_ProductoDAO facturaProductoDAO = new MySQLFactura_ProductoDAO(cn);

            // Cargar datos desde CSV
            ClienteCsvLoader clienteLoader = new ClienteCsvLoader(clienteDAO);
            ProductoCsvLoader productoLoader = new ProductoCsvLoader(productoDAO);
            FacturaCsvLoader facturaLoader = new FacturaCsvLoader(facturaDAO);
            FacturaProductoCsvLoader facturaProductoLoader = new FacturaProductoCsvLoader(facturaProductoDAO);

            clienteLoader.cargar("src/main/resources/data/clientes.csv");
            facturaLoader.cargar("src/main/resources/data/facturas.csv");
            productoLoader.cargar("src/main/resources/data/productos.csv");
            facturaProductoLoader.cargar("src/main/resources/data/facturas-productos.csv");

            System.out.println("Datos cargados desde los CSV correctamente.");

        } catch (SQLException e) {
            System.err.println("Error de conexión o SQL: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
