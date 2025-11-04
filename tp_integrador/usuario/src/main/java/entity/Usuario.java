package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

    public class Usuario {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nombre;
        private String  mail;
        private int celular;
        private enum rol;
        @OneToMany
        private List<Cuenta> cuentas;
         @OneToMany
        private List<Monopatin> monopatines;
        @OneToMany
        private List<Viaje> viajes;
    }

}
