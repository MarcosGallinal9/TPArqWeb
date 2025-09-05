package Factory;

import DAO.PersonaDAO;
import mysql.MySqlJDBCDAOFactory;

public abstract class DAOFactory {
    private static volatile DAOFactory instance;
    public static DAOFactory getInstance(DBType type) {
        if (instance == null) {
            synchronized (DAOFactory.class) {
                if (instance == null) {
                    switch (type) {
                        case MYSQL:
                            instance = new MySqlJDBCDAOFactory();
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
    public abstract PersonaDAO createPersonaDAO();
}

