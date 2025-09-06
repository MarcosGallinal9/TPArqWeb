import DAO.ClienteDAO;
import entities.Cliente;
import mysql.MySQLClienteDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Datos de conexión a tu base de datos (ajusta según tu config)
        final String url = "jdbc:mysql://localhost:3306/integrador";
        final String user = "root";
        final String password = "password"; // o la que tengas configurada

        try (Connection cn = DriverManager.getConnection(url, user, password)) {
            // Crear el DAO
            ClienteDAO clienteDAO = new MySQLClienteDAO(cn);

            // Crear un cliente de prueba
            Cliente cliente = new Cliente();
            cliente.setNombre("Marcos");
            cliente.setEmail("marcos@example.com");

            // Insertar el cliente
            clienteDAO.crearCliente(cliente);

            System.out.println("✅ Cliente insertado correctamente en la BD.");

        } catch (SQLException e) {
            System.err.println("❌ Error de conexión o SQL: " + e.getMessage());
        }
    }
}
