package org.iesvdm.videoclub.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.iesvdm.videoclub.domain.Actor;
import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Idioma;
import org.iesvdm.videoclub.domain.Pelicula;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


    public class PeliculaDTO {

        private Long id;
        private String titulo;
        private Set<Categoria>  categorias;
        private int numCategorias;

       /*
       @Transient
        private int numCatPorPelicula;
        */

    public PeliculaDTO(Long id, String titulo, HashSet<Categoria> categorias) {
        this.id = id;
        this.titulo = titulo;
        this.categorias = categorias;

        //this.numCatPorPelicula = getCategorias().size();
    }
}


