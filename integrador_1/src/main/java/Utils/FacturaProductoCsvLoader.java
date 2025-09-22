package Utils;

import DAO.Factura_ProductoDAO;
import entities.Factura_Producto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class FacturaProductoCsvLoader {
    private final Factura_ProductoDAO facturaProductoDAO;

    public FacturaProductoCsvLoader(Factura_ProductoDAO facturaProductoDAO) {
        this.facturaProductoDAO = facturaProductoDAO;
    }

    public void cargar(String filePath) {
        try (FileReader reader = new FileReader(filePath, StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .parse(reader)) {

            for (CSVRecord row : parser) {
                int idFactura = Integer.parseInt(row.get("idFactura"));
                int idProducto = Integer.parseInt(row.get("idProducto"));
                int cantidad = Integer.parseInt(row.get("cantidad"));

                Factura_Producto fp = new Factura_Producto(idFactura, idProducto, cantidad);
                facturaProductoDAO.crearFactura_Producto(fp);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
