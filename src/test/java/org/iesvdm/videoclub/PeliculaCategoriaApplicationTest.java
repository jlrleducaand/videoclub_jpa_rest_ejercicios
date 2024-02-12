package org.iesvdm.videoclub;

import jakarta.transaction.Transactional;
import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Comentario;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.domain.Tutorial;
import org.iesvdm.videoclub.repository.CategoriaRepository;
import org.iesvdm.videoclub.repository.ComentarioRepository;
import org.iesvdm.videoclub.repository.PeliculaRepository;
import org.iesvdm.videoclub.repository.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;

@SpringBootTest
public class PeliculaCategoriaApplicationTest {



        @Autowired
        PeliculaRepository peliculaRepository;

        @Autowired
        CategoriaRepository categoriaRepository;

        @Test
        void contextLoads() {
        }

        @Test
        void guardarManyToMany(){

            Pelicula pelicula1 = new Pelicula(0, "Pelicula1", new HashSet<>());
            peliculaRepository.save(pelicula1);

           /*
           Categoria categoria1 = new Categoria(0, "Categoria1", new HashSet<>());
            categoriaRepository.save(categoria1);

            Categoria categoria2 = new Categoria(0, "Categoria2", new HashSet<>());
            categoriaRepository.save(categoria2);
            */


        }


}
