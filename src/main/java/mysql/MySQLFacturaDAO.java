package mysql;

import DAO.FacturaDAO;
import entities.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLFacturaDAO implements FacturaDAO {
    private final Connection cn;

    public MySQLFacturaDAO(Connection cn) {
        this.cn = cn;
        crearTablaSiNoExiste();
    }
    private void crearTablaSiNoExiste() {

    }

    @Override
    public void crearFactura(Factura factura) {
        String sql = "INSERT INTO Factura (idFactura,idCliente) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, factura.getIdFactura());
            ps.setInt(2, factura.getIdCliente());
            ps.executeUpdate();
            cn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
