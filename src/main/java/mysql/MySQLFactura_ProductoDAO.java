package mysql;

import DAO.Factura_ProductoDAO;
import entities.Factura_Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLFactura_ProductoDAO implements Factura_ProductoDAO {
    private final Connection cn;

    public MySQLFactura_ProductoDAO(Connection cn) {
        this.cn = cn;
        crearTablaSiNoExiste();
    }
    private void crearTablaSiNoExiste() {

    }

    @Override
    public void crearFactura_Producto(Factura_Producto f) {
        String sql = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, f.getIdFactura());
            ps.setInt(2, f.getIdProducto());
            ps.setInt(3, f.getCantidad());
            ps.executeUpdate();
//            cn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
