package mysql;

import DAO.ProductoDAO;
import entities.Producto;

import java.sql.Connection;

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

    }
}
