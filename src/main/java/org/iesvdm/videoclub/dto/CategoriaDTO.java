package org.iesvdm.videoclub.dto;

import jakarta.persistence.*;
import lombok.*;
import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Pelicula;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaDTO {


      private Categoria categoria;
      private int conteoPeliculas;


}
