package DAO;

import entities.Cliente;
import entities.ClienteConTotal;

import java.util.ArrayList;


public interface ClienteDAO {
    void crearCliente(Cliente cliente);
    ArrayList<ClienteConTotal> listarClientesMasFacturados();
}
