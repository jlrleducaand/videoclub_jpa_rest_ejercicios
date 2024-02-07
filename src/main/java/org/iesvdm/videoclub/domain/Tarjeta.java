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
    @OneToOne(mappedBy = "tarjeta")
    private Socio socio;


}
