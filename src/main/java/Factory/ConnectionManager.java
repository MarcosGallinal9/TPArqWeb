package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public final class ConnectionManager {
    private static volatile ConnectionManager instance;


    private static final String url = "jdbc:mysql://localhost:3306/integrador";
    private static final String user = "root";
    private static final String password = "";

    private ConnectionManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error cargando el driver de MySQL", e);
        }
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            synchronized (ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }
        }
        return instance;
    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }



}
