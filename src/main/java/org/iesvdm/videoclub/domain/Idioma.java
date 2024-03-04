package org.iesvdm.videoclub.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name="idioma")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//Si utilizo @OneToMany(FetchType.LAZY) además debo usar
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// Para evitar que se envíe información de serialización
// JSON sobre los handler e hibernateLazyInitializer
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_idioma")
    private Long id;
    private String nombre;

    @Column(name = "ultima_actualizacion")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private Date ultimaActualizacion;

    @OneToMany(mappedBy = "idioma", fetch = FetchType.EAGER)
    @JsonIgnore         //Rompe el lazo de Serializacion
    @ToStringExclude    //Rompe el lazo de Serializacion
    private Set<Pelicula> peliculasIdioma;

    @OneToMany(mappedBy = "idiomaOriginal")
    @JsonIgnore         //Rompe el lazo de Serializacion
    @ToStringExclude    //Rompe el lazo de Serializacion
    private Set<Pelicula> peliculasIdiomaOriginal;

    // ******** CONSTRUCTOR PARA TESTS **************
    public Idioma(long id, String nombre, Set<Pelicula> peliculasIdioma) {
        this.id = id;
        this.nombre = nombre;
        this.peliculasIdioma = peliculasIdioma;
    }

    public Idioma(long id, String español, boolean add) {
    }
}
