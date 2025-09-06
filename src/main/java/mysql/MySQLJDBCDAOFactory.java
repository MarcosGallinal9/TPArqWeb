package mysql;

import DAO.ClienteDAO;
import DAO.FacturaDAO;
import DAO.Factura_ProductoDAO;
import DAO.ProductoDAO;
import Factory.ConnectionManager;
import Factory.DAOFactory;

public class MySQLJDBCDAOFactory extends DAOFactory {

    @Override
    public ProductoDAO getProductoDAO() {
        return new MySQLProductoDAO(ConnectionManager.getInstance().getConnection());
    }

    @Override
    public ClienteDAO getClienteDAO() {
        return new MySQLClienteDAO(ConnectionManager.getInstance().getConnection());
    }

    @Override
    public FacturaDAO getFacturaDAO() {
        return new MySQLFacturaDAO(ConnectionManager.getInstance().getConnection());
    }

    @Override
    public Factura_ProductoDAO getFactura_ProductoDAO() {
        return new MySQLFactura_ProductoDAO(ConnectionManager.getInstance().getConnection());
    }
}
