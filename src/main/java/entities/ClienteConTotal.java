package entities;

public class ClienteConTotal extends Cliente{

    private double totalFacturado;

    public ClienteConTotal(int idCliente, String nombre, String email, int  totalFacturado) {
        super(idCliente, nombre, email);
        this.totalFacturado = totalFacturado;
    }

    public double getTotalFacturado() {

        return totalFacturado;
    }

    public void setTotalFacturado(double totalFacturado) {

        this.totalFacturado = totalFacturado;
    }

}
