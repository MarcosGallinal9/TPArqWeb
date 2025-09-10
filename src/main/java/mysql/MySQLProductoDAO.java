package mysql;

import DAO.ProductoDAO;
import Factory.ConnectionManager;
import entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLProductoDAO implements ProductoDAO {


    public MySQLProductoDAO() {

    }



    @Override
    public void crearProducto(Producto p) {
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
            ps = cn.prepareStatement(sql);

            ps.setInt(1, p.getIdProducto());
            ps.setString(2, p.getNombre());
            ps.setDouble(3, p.getPrecio());
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

    public String productoMasRecaudado() {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String nombreProducto = null;

        try {
            cn = ConnectionManager.getInstance().getConnection();
            String sql = """
                SELECT p.nombre
                FROM Producto p
                JOIN Factura_Producto f ON p.idProducto = f.idProducto
                GROUP BY p.idProducto, p.nombre
                ORDER BY SUM(f.cantidad * p.valor) DESC
                LIMIT 1
            """;

            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                nombreProducto = rs.getString("nombre");
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

        return nombreProducto;
    }
}