package mysql;

import DAO.ProductoDAO;
import entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLProductoDAO implements ProductoDAO {
    private final Connection cn;

    public MySQLProductoDAO(Connection cn) {
        this.cn = cn;
    }



    @Override
    public void crearProducto(Producto p) {
        String sql = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdProducto());
            ps.setString(2, p.getNombre());
            ps.setDouble(3, p.getPrecio());
            ps.executeUpdate();
//            cn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String productoMasRecaudado() {
        String sql = "SELECT p.nombre " +
                "     FROM Producto p JOIN Factura_Producto f ON p.idProducto= f.idProducto " +
                "     GROUP BY p.idProducto , p.nombre " +
                "     ORDER BY SUM(f.cantidad * p.valor) DESC " +
                "     LIMIT 1;";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ResultSet rs= ps.executeQuery();
                if(rs.next()){
                    String nombreProducto= rs.getString("nombre");
                    return nombreProducto;
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}