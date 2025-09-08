package mysql;

import DAO.ClienteDAO;
import entities.Cliente;
import entities.ClienteConTotal;

import java.sql.*;
import java.util.ArrayList;

public class MySQLClienteDAO implements ClienteDAO {
    private final Connection cn;

    public MySQLClienteDAO(Connection cn) {
        this.cn = cn;

    }



    @Override
    public void crearCliente(Cliente cliente) {
        final String sql = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, cliente.getIdCliente());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<ClienteConTotal> listarClientesMasFacturados() {
        ArrayList<ClienteConTotal> clientes = new ArrayList<>();
        String sql = """
SELECT c.idCliente , c.nombre, c.email , SUM(fp.cantidad * p.valor) AS total_facturado 
FROM Cliente c JOIN Factura f ON c.idCliente = f.idCliente 
JOIN Factura_Producto fp ON f.idFactura = fp.idFactura 
JOIN Producto p ON fp.idProducto = p.idProducto 
GROUP BY c.idCliente 
ORDER BY total_facturado DESC""";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ResultSet rs= ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                int totalFacturado = rs.getInt("total_facturado");
                ClienteConTotal c= new ClienteConTotal (id, nombre, email, totalFacturado);
                clientes.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
