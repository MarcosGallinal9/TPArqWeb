package Utils;

import DAO.ClienteDAO;
import entities.Cliente;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ClienteCsvLoader {
    private final ClienteDAO clienteDAO;

    public ClienteCsvLoader(ClienteDAO clienteDAO) {

        this.clienteDAO = clienteDAO;
    }

    public void cargar(String filePath) {
        try (FileReader reader = new FileReader(filePath, StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .parse(reader)) {

            for (CSVRecord row : parser) {
                int idCliente = Integer.parseInt(row.get("idCliente"));
                String nombre = row.get("nombre");
                String email = row.get("email");

                Cliente c = new Cliente(idCliente, nombre, email);
                clienteDAO.crearCliente(c);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
