package org.iesvdm.videoclub.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="pelicula")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pelicula")
    @EqualsAndHashCode.Include
    private long id;

    private String titulo;

    private String descripcion;
    @Column(name = "anyo_lanzamiento")
    @JsonFormat(pattern = "yyyy",  shape = JsonFormat.Shape.STRING)
    private Date anyoLanzamiento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_idioma", nullable = true) //nulable para tests
    //@JasonBackReference
    @ToString.Exclude
    private Idioma idioma;

    @ManyToOne()
    @JoinColumn(name = "id_idioma_original")
    private Idioma idiomaOriginal;

    @Column(name = "duracion_alquiler")
    private int duracionAlquiler;

    @Column(name = "rental_rate")
    private BigDecimal rentalRate;
    private int duracion;

    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;
    private String clasificacion;

    @Column(name = "caracteristicas_especiales")
    private String caracteristicasEspeciales;

    @ManyToMany(mappedBy = "peliculas", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,

    },fetch = FetchType.EAGER )
/*
    ****** ESTAS DOS ESTRATEGIAS NO `PUEDEN TRABAJAR JUNTAS  ******

   @JoinTable(
            name = "pelicula_categoria",
            joinColumns = @JoinColumn(name = "id_pelicula", referencedColumnName = "id_pelicula"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria"))
*/

    private Set<Categoria> categorias = new HashSet<>();


    @ManyToMany(mappedBy = "peliculas", cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
    },fetch = FetchType.EAGER)
// ****** ESTAS DOS ESTRATEGIAS NO `PUEDEN TRABAJAR JUNTAS  ******
    /*
    @JoinTable(
            name = "pelicula_actor",
            joinColumns = @JoinColumn(name = "id_pelicula", referencedColumnName = "id_pelicula"),
          inverseJoinColumns = @JoinColumn(name = "id_actor", referencedColumnName = "id_actor"))
    */
    private Set<Actor>  actores = new HashSet<>();


    @Column(name = "ultima_actualizacion")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private Date ultimaActualizacion;


    //Constructores `para tests
    public <E> Pelicula(long id, String titulo, HashSet<Categoria>  categorias) {
        this.id = id;
        this.titulo = titulo;
        this.categorias = categorias;
    }
    public <E> Pelicula(long id, String titulo, HashSet<Categoria>  categorias, Idioma idioma) {
        this.id = id;
        this.titulo = titulo;
        this.categorias = categorias;
        this.idioma = idioma;
    }

    public Pelicula(int id, String titulo, Idioma idioma1) {
        this.id = id;
        this.titulo = titulo;
        this.idioma = idioma;
    }

    //Constructor Peliculas Categorias
    public Pelicula(Integer id, String titulo, Idioma idioma, HashSet<Categoria> categoria ) {
        this.id = id;
        this.titulo = titulo;
        this.idioma = idioma;
        this.categorias = categorias;
    }

    public <E> Pelicula(Integer integer, String s, HashSet<E> es) {
        this.id = integer;
        this.titulo = s;
        this.categorias = (Set<Categoria>) es;
    }
    // PARA ACTOR
    public Pelicula(int id, String programación, HashSet<Categoria> categorias, HashSet<Actor> actores) {
     this.id = id;
     this.titulo = titulo;
     this.categorias = categorias;
     this.actores = actores;
    }
}
