package mysql;

import DAO.FacturaDAO;
import entities.Factura;

import java.sql.Connection;

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

    }
}
