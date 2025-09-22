import DAO.*;
import Factory.ConnectionManager;
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
//        url = "jdbc:mysql://localhost:3306/integrador";
//        user = "root";
//        password = ""; //Contraseña vacia

        // Crear esquema
        Connection cn = null;
        try {
            cn = ConnectionManager.getInstance().getConnection();
            CreadorEsquema.crearEsquema(cn);
            System.out.println("Esquema creado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            return; // aborta si falla la creación de tablas
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            // DAOs
            ClienteDAO clienteDAO = new MySQLClienteDAO();
            ProductoDAO productoDAO = new MySQLProductoDAO();
            FacturaDAO facturaDAO = new MySQLFacturaDAO();
            Factura_ProductoDAO facturaProductoDAO = new MySQLFactura_ProductoDAO();

            // Loaders CSV
            ClienteCsvLoader clienteLoader = new ClienteCsvLoader(clienteDAO);
            ProductoCsvLoader productoLoader = new ProductoCsvLoader(productoDAO);
            FacturaCsvLoader facturaLoader = new FacturaCsvLoader(facturaDAO);
            FacturaProductoCsvLoader facturaProductoLoader = new FacturaProductoCsvLoader(facturaProductoDAO);

            // Cargar datos
            clienteLoader.cargar("src/main/resources/data/clientes.csv");
            facturaLoader.cargar("src/main/resources/data/facturas.csv");
            productoLoader.cargar("src/main/resources/data/productos.csv");
            facturaProductoLoader.cargar("src/main/resources/data/facturas-productos.csv");

            System.out.println("Datos cargados correctamente.");

            // Consultas

            String prod = productoDAO.productoMasRecaudado();
            System.out.println("Producto más recaudado: " + prod);

            ArrayList<ClienteConTotal> listado = clienteDAO.listarClientesMasFacturados();
            System.out.println("Clientes más facturados: " + listado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
