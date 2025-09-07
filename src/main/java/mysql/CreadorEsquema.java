package mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreadorEsquema {
    public static void createSchema(Connection conn) throws SQLException {

        try (Statement st = conn.createStatement()) {

            // Borrar tablas si existen (orden correcto por claves foráneas)
            st.execute("DROP TABLE IF EXISTS Factura_Producto");
            st.execute("DROP TABLE IF EXISTS Factura");
            st.execute("DROP TABLE IF EXISTS Producto");
            st.execute("DROP TABLE IF EXISTS Cliente");

            // Crear tablas
            String sqlCliente = """
                CREATE TABLE Cliente(
                    idCliente INT PRIMARY KEY,
                    nombre VARCHAR(500),
                    email VARCHAR(150)
                );
            """;

            String sqlProducto = """
                CREATE TABLE Producto(
                    idProducto INT PRIMARY KEY,
                    nombre VARCHAR(45),
                    valor FLOAT
                );
            """;

            String sqlFactura = """
                CREATE TABLE Factura(
                    idFactura INT PRIMARY KEY,
                    idCliente INT,
                    FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)
                );
            """;

            String sqlFacturaProducto = """
                CREATE TABLE Factura_Producto(
                    idFactura INT,
                    idProducto INT,
                    cantidad INT,
                    PRIMARY KEY (idFactura, idProducto),
                    FOREIGN KEY (idFactura) REFERENCES Factura(idFactura),
                    FOREIGN KEY (idProducto) REFERENCES Producto(idProducto)
                );
            """;

            st.execute(sqlCliente);
            st.execute(sqlProducto);
            st.execute(sqlFactura);
            st.execute(sqlFacturaProducto);


            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }
}
