package mysql;

import DAO.ClienteDAO;
import Factory.ConnectionManager;
import entities.Cliente;
import entities.ClienteConTotal;

import java.sql.*;
import java.util.ArrayList;

public class MySQLClienteDAO implements ClienteDAO {


    public MySQLClienteDAO() {


    }



    @Override
    public void crearCliente(Cliente cliente) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
            ps = cn.prepareStatement(sql);

            ps.setInt(1, cliente.getIdCliente());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getEmail());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<ClienteConTotal> listarClientesMasFacturados() {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<ClienteConTotal> clientes = new ArrayList<>();

        try {
            cn = ConnectionManager.getInstance().getConnection();
            String sql = """
                SELECT c.idCliente, c.nombre, c.email, SUM(fp.cantidad * p.valor) AS total_facturado
                FROM Cliente c
                JOIN Factura f ON c.idCliente = f.idCliente
                JOIN Factura_Producto fp ON f.idFactura = fp.idFactura
                JOIN Producto p ON fp.idProducto = p.idProducto
                GROUP BY c.idCliente
                ORDER BY total_facturado DESC
            """;

            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                int totalFacturado = rs.getInt("total_facturado");

                clientes.add(new ClienteConTotal(id, nombre, email, totalFacturado));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clientes;
    }
}
