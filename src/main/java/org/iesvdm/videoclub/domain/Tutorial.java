package org.iesvdm.videoclub.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity  // si n o hay entity no  se creara la tabla en la bbdd
@Table(name = "tutorial", schema = "appbbdd", indexes = {@Index(name="title_Index", columnList = "titulo", unique = false)})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tutorial")
    @EqualsAndHashCode.Include
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
    @OneToMany(mappedBy = "tutorial", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comentario> comentarios = new HashSet<>();

    //********  Constructor para Tests   ******
    public Tutorial(int i, String titulo, HashSet<Comentario> comentarios) {
        this.id = id;
        this.titulo = titulo;
        this.comentarios = comentarios;
        this.fechaPublicacion= new Date();
    }


    //*******  Metodos Helper  ********
    public Tutorial addComentario(Comentario comentario){
        comentario.setTutorial(this);
        this.comentarios.add(comentario);

        return this;
    }

    public Tutorial removeComentario(Comentario comentario){
        this.comentarios.remove(comentario);
        comentario.setTutorial(null);

        return this;
    }

    // Todos los metodos cuando esta LAZY deben ser anotados con @Transactional
    // Si el tipo de Fecht es EAGER no hará falta
    // entra en bucle, ya que se llaman en bulcle para que solo de una vuelta @ToString. Exclude en el Padre

}
