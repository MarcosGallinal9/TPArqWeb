package mysql;

import DAO.ClienteDAO;
import entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLClienteDAO implements ClienteDAO {
    private final Connection cn;

    public MySQLClienteDAO(Connection cn) {
        this.cn = cn;
        crearTablaSiNoExiste();
    }
    private void crearTablaSiNoExiste() {
        final String persona = "CREATE TABLE IF NOT EXISTS cliente (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) NOT NULL" +
                ")";

        try (Statement st = cn.createStatement()) {
            st.execute(persona);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void crearCliente(Cliente cliente) {
        final String sql = "INSERT INTO cliente (nombre, email) VALUES (?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
