package org.iesvdm.videoclub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity  // si n o hay entity no  se creara la tabla en la bbdd
@Table(name = "tutorial", schema = "appbbdd", indexes = {@Index(name="title_Index", columnList = "titulo", unique = false)})

public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tutorial")
    private long id;

    @Column(name = "titulo", length = 50)
    private String titulo;

    @Column(name = "descripcion", length = 256)
    private String descripcion;

    @Column(name = "public")
    private Boolean publicado;

    @Column(nullable = false)
    private Date fechaPublicacion;

    // con mappedBY  solo arrastra la clave y crea una FK
    // POR LO QUE NO SE ESTABLECE LA RELACION BIDIRECCIONAL
    @OneToMany(mappedBy = "tutorial", fetch = FetchType.LAZY )
    private List<Comentario> comentarios = new ArrayList<>();

    // todos los metodos cuando esta LAZY  deben ser anotados con  @Transactional
    // Si el tipo de Fecht es EAGER  no hará falta
    // entra en bucle  ya que se llaman en bulcle para que solo de una vuelta @ToString.Exclude en el Padre

}
