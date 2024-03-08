package org.iesvdm.videoclub.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.iesvdm.videoclub.service.CategoriaService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "categoria")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  //solo los que tienen include
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    @EqualsAndHashCode.Include
    private long id;

    //@NaturalId
    @EqualsAndHashCode.Include
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "pelicula_categoria",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    Set<Pelicula> peliculas = new HashSet<>();

    @Column(name = "ultima_actualizacion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private Date ultimaActualizacion;

    //Campo del que no queremos que se haga columna en la tabla
    @Transient
    private int numPelis;

    //Constructores para los tests
    public  Categoria(long id, String nombre, HashSet<Pelicula> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.peliculas = peliculas;
    }
    public  Categoria(long id, String nombre, HashSet<Pelicula> peliculas, Date ultimaActualizacion) {
        this.id = id;
        this.nombre = nombre;
        this.peliculas = peliculas;
        this.ultimaActualizacion = ultimaActualizacion;

          }



}