package mysql;

import DAO.Factura_ProductoDAO;
import Factory.ConnectionManager;
import entities.Factura_Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLFactura_ProductoDAO implements Factura_ProductoDAO {


    public MySQLFactura_ProductoDAO() {

    }


    @Override
    public void crearFactura_Producto(Factura_Producto f) {
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
            ps = cn.prepareStatement(sql);

            ps.setInt(1, f.getIdFactura());
            ps.setInt(2, f.getIdProducto());
            ps.setInt(3, f.getCantidad());
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
}
