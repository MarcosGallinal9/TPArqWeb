package mysql;

import DAO.ClienteDAO;
import entities.Cliente;

import java.sql.Connection;

public class MySQLClienteDAO implements ClienteDAO {
    private final Connection cn;

    public MySQLClienteDAO(Connection cn) {
        this.cn = cn;
        crearTablaSiNoExiste();
    }
    private void crearTablaSiNoExiste() {

    }


    @Override
    public void crearCliente(Cliente cliente) {

    }
}
