package Factory;

import DAO.ClienteDAO;
import DAO.FacturaDAO;
import DAO.Factura_ProductoDAO;
import DAO.ProductoDAO;
import mysql.MySQLClienteDAO;
import mysql.MySQLFacturaDAO;
import mysql.MySQLFactura_ProductoDAO;
import mysql.MySQLProductoDAO;

public class MySQLJDBCDAOFactory extends DAOFactory {

    @Override
    public ProductoDAO getProductoDAO() {
        return new MySQLProductoDAO();
    }

    @Override
    public ClienteDAO getClienteDAO() {
        return new MySQLClienteDAO();
    }

    @Override
    public FacturaDAO getFacturaDAO() {
        return new MySQLFacturaDAO();
    }

    @Override
    public Factura_ProductoDAO getFactura_ProductoDAO() {
        return new MySQLFactura_ProductoDAO();
    }
}
