package Utils;

import DAO.ProductoDAO;
import entities.Producto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class ProductoCsvLoader {
    private final ProductoDAO productoDAO;

    public ProductoCsvLoader(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public void cargar(String filePath) {
        try (FileReader reader = new FileReader(filePath, StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .parse(reader)) {

            for (CSVRecord row : parser) {
                int idProducto = Integer.parseInt(row.get("idProducto"));
                String nombre = row.get("nombre");
                float valor = Float.parseFloat(row.get("valor"));

                Producto p = new Producto(idProducto, nombre, valor);
                productoDAO.crearProducto(p); // persistimos en la BD
            }
            System.out.println("Productos cargados desde " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
