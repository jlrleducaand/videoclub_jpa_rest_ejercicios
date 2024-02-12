package org.iesvdm.videoclub.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "categoria")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    @EqualsAndHashCode.Include
    private long id;

    private String nombre;

    @ManyToMany( mappedBy = "categorias")
    @JsonIgnore
    Set<Pelicula> peliculas = new HashSet<>();

    @Column(name = "ultima_actualizacion")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private Date ultimaActualizacion;


    //Constructor
    public <E> Categoria(long id, String nombre, HashSet<Pelicula> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.peliculas = peliculas;
    }
}