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
        crearTablaSiNoExiste();
    }
    private void crearTablaSiNoExiste() {

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


}
