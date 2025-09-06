package mysql;

import DAO.Factura_ProductoDAO;
import entities.Factura_Producto;

import java.sql.Connection;

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

    }
}
