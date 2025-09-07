package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public final class ConnectionManager {
    private static volatile ConnectionManager instance;
    private final Connection con;

    private static final String url = "jdbc:mysql://localhost:3306/Entregable1";
    private static final String user = "user";
    private static final String password = "";

    private ConnectionManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            this.con = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
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


    public Connection getConnection() {

        return con;
    }

    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
