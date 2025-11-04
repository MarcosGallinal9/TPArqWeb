package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    @Id
    private int nroCuenta;
    private double monto;
    private boolean estado;
    private Date fechaAlta;
    private enum tipoCuenta;
    @ManyToMany
    private List<Usuario> usuarios = new List<>();




}
