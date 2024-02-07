package org.iesvdm.videoclub.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Socio {

    @Id
    @Column(length = 9)  //Sin Espacios
    private String dni;

    private String nombre;

    private String apellidos;

    @OneToOne
    @JoinColumn(name = "id_Tarjeta")
    private Tarjeta tarjeta;
}
