import DAO.*;
import Utils.*;
import entities.*;
import mysql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Datos de conexión a base de datos
        final String url = "jdbc:mysql://localhost:3306/integrador";
        final String user = "root";
        final String password = ""; //Contraseña vacia

        try (Connection cn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexión establecida con la BD.");
            // Crea el esquema en la base de datos
            CreadorEsquema creador = new CreadorEsquema();
            creador.crearEsquema(cn);
            System.out.println("Esquema creado correctamente.");

            // Crea DAOs
            ClienteDAO clienteDAO = new MySQLClienteDAO(cn);
            ProductoDAO productoDAO = new MySQLProductoDAO(cn);
            FacturaDAO facturaDAO = new MySQLFacturaDAO(cn);
            Factura_ProductoDAO facturaProductoDAO = new MySQLFactura_ProductoDAO(cn);

            // Carga datos desde CSV
            ClienteCsvLoader clienteLoader = new ClienteCsvLoader(clienteDAO);
            ProductoCsvLoader productoLoader = new ProductoCsvLoader(productoDAO);
            FacturaCsvLoader facturaLoader = new FacturaCsvLoader(facturaDAO);
            FacturaProductoCsvLoader facturaProductoLoader = new FacturaProductoCsvLoader(facturaProductoDAO);

            //Toma los datos desde los archivos
            clienteLoader.cargar("src/main/resources/data/clientes.csv");
            facturaLoader.cargar("src/main/resources/data/facturas.csv");
            productoLoader.cargar("src/main/resources/data/productos.csv");
            facturaProductoLoader.cargar("src/main/resources/data/facturas-productos.csv");

            System.out.println("Datos cargados desde los CSV correctamente.");

//            Escriba un programa JDBC que retorne el producto que más recaudó. Se define
//            “recaudación” como cantidad de productos vendidos multiplicado por su valor.

            String prod= productoDAO.productoMasRecaudado();
            System.out.println("Producto mas recaudado:" + prod);

//            Escriba un programa JDBC que imprima una lista de clientes, ordenada por a cuál se le
//            facturó más.

            ArrayList<ClienteConTotal> listado= clienteDAO.listarClientesMasFacturados();
            System.out.println("Clientes mas facturados:" + listado);

        } catch (SQLException e) {
            System.err.println("Error de conexión o SQL: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
