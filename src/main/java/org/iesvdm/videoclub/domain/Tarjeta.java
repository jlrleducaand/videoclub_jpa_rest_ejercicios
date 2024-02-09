package org.iesvdm.videoclub.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tarjeta {


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date Caducidad;

    @NotNull
    // En vez de hacer una columna mapea la tarjeta en Socio como una columna FK
    // Socio ya existe por lo que no se entiende traer de nuevo el socio
    @OneToOne(mappedBy = "tarjeta")
    private Socio socio;


}
