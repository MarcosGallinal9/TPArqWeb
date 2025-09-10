package mysql;

import DAO.FacturaDAO;
import Factory.ConnectionManager;
import entities.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLFacturaDAO implements FacturaDAO {


    public MySQLFacturaDAO() {

    }


    @Override
    public void crearFactura(Factura factura) {
        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";
            ps = cn.prepareStatement(sql);

            ps.setInt(1, factura.getIdFactura());
            ps.setInt(2, factura.getIdCliente());
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
