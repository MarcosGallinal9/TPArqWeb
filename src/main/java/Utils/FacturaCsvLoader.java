package Utils;

import DAO.FacturaDAO;
import entities.Factura;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class FacturaCsvLoader {
    private final FacturaDAO facturaDAO;

    public FacturaCsvLoader(FacturaDAO facturaDAO) {

        this.facturaDAO = facturaDAO;
    }

    public void cargar(String filePath) {
        try (FileReader reader = new FileReader(filePath, StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .parse(reader)) {

            for (CSVRecord row : parser) {
                int idFactura = Integer.parseInt(row.get("idFactura"));
                int idCliente = Integer.parseInt(row.get("idCliente"));

                Factura factura = new Factura(idFactura, idCliente);
                facturaDAO.crearFactura(factura);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
