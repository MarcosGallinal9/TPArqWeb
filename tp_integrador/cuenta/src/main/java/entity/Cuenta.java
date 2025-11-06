package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    @Id
    private int nroCuenta;
    private double monto;
    private boolean estado;
    private Date fechaAlta;
    private String tipoCuenta;
    private List<Integer> usuarios ;




}
