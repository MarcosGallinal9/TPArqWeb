package Factory;


import DAO.ClienteDAO;
import DAO.FacturaDAO;
import DAO.Factura_ProductoDAO;
import DAO.ProductoDAO;
import mysql.*;

public abstract class DAOFactory {
    private static volatile DAOFactory instance;
    public static DAOFactory getInstance(DBType type) {
        if (instance == null) {
            synchronized (DAOFactory.class) {
                if (instance == null) {
                    switch (type) {
                        case MYSQL:
                            instance = new MySQLJDBCDAOFactory();
                            break;
                        //case DERBY_JDBC: return new DerbyJDBCDAOFactory();
                        //case JPA_HIBERNATE: return new HibernateJDBCDAOFactory();
                        default:
                            return null;
                    }
                }
            }
        }
        return null;
    }
    public abstract ProductoDAO getProductoDAO();
    public abstract ClienteDAO getClienteDAO();
    public abstract FacturaDAO getFacturaDAO();
    public abstract Factura_ProductoDAO getFactura_ProductoDAO();


}

