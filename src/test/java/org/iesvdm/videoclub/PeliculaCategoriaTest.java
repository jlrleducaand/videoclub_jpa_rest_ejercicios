package org.iesvdm.videoclub;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.IdentifiableType;
import org.iesvdm.videoclub.domain.*;
import org.iesvdm.videoclub.repository.*;
import org.iesvdm.videoclub.util.UtilJPA;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PeliculaCategoriaTest {

        @Autowired
        PeliculaRepository peliculaRepository;

        @Autowired
        CategoriaRepository categoriaRepository;

        @Autowired
        IdiomaRepository idiomaRepository;

        @PersistenceContext
        EntityManager entityManager;

        @Test
        void contextLoads() {
        }


        @Autowired
        private PlatformTransactionManager transactionManager;
        private TransactionTemplate transactionTemplate;

        @BeforeEach
        public void setUp() {
                transactionTemplate = new TransactionTemplate(transactionManager);
        }

        @Test
        @Order(1)
        void grabarPorPropietarioDeManyToMany() {
                Idioma idioma1 = new Idioma(0, "Espaniol", new HashSet<Pelicula>());
                idiomaRepository.save(idioma1);

                Idioma idioma2 = new Idioma(0, "Ingles", new HashSet<Pelicula>());
                idiomaRepository.save(idioma2);

                Pelicula pelicula1 = new Pelicula(0, "Programación", new HashSet<Categoria>(), idioma1);
                peliculaRepository.save(pelicula1);


                Pelicula pelicula2 = new Pelicula(0, "Base de datos", new HashSet<Categoria>(), idioma2);
                peliculaRepository.save(pelicula2);

                Categoria categoria1 = new Categoria(0, "Categoria1 - Programando fácil con JPA :P", new HashSet<>());

                categoria1.getPeliculas().add(pelicula1);
                //RECUERDA QUE LA COLECCIÓN DE CATEGORIAS ES UN SET Y NO PUEDE HABER REPETIDOS CON EL
                //MISMO ID por eso se graba cada uno
                categoriaRepository.save(categoria1);

                pelicula1.getCategorias().add(categoria1);
                peliculaRepository.save(pelicula1);

                Categoria categoria2 = new Categoria(0, "Categoria2 - Programando fácil2 con JPA :P", new HashSet<>());

                categoria2.getPeliculas().add(pelicula2);
                categoriaRepository.save(categoria1);

                pelicula2.getCategorias().add(categoria2);
                peliculaRepository.save(pelicula2);

        }




                @Test
                @Order(2)
                void grabarPeliculaQueYaExiste() {

                        Pelicula pelicula3 = new Pelicula(0, "EEEH Pelicula 3!!!!", new HashSet<>());
                        peliculaRepository.save(pelicula3);

                        Categoria categoria2 = new Categoria(0, "Categoria2 - NO programando tan fácilmente...", new HashSet<>());
                        categoriaRepository.save(categoria2);

                        //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
                        //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                       /* List<Pelicula> peliculas = entityManager.createQuery(
                       "select t " +
                               "from Pelicula t " +
                               "join fetch t.categorias ps " +
                               "where ps.id = :id", Pelicula.class)
                                .setParameter("id", categoria2.getId())
                                .getResultList();

                        categoria2.setPeliculas(new HashSet<>(peliculas));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Pelicula.class,
                                Categoria.class,
                                categoria2.getId(),
                                categoria2::setPeliculas
                        );*/


                        Pelicula pelicula1 = peliculaRepository.findById(1L).orElse(null);
                      //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
                      //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                       /* List<Categoria> categorias = entityManager.createQuery(
                                "select p " +
                                        "from Categoria p " +
                                        "join fetch p.peliculas ts " +
                                        "where ts.id = :id", Categoria.class)
                                .setParameter("id", pelicula1.getId())
                                .getResultList();
                        pelicula1.setCategorias(new HashSet<>(categorias));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Categoria.class,
                                Pelicula.class,
                                pelicula1.getId(),
                                pelicula1::setCategorias
                        );*/

                        categoria2.getPeliculas().add(pelicula1);
                        categoria2.getPeliculas().add(pelicula3);
                        categoriaRepository.save(categoria2);
                }


                @Test
                @Order(3)
                void desasociarPelicula() {

                        Pelicula pelicula3 = peliculaRepository.findById(3L).orElse(null);
                        //Si se utlizas un fetch LAZY, la mejor estrategia es realizar un join fetch en JPQL
                        //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                        /*List<Categoria> categorias = entityManager.createQuery(
                        "select p " +
                                "from Categoria p " +
                                "join fetch p.peliculas ts " +
                                "where ts.id = :id", Categoria.class)
                                .setParameter("id", pelicula.getId())
                                .getResultList();

                        pelicula.setCategorias(new HashSet<>(categorias));
                                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                                Categoria.class,
                                                Pelicula.class,
                                                pelicula1.getId(),
                                                pelicula1::setCategorias
                        );
                        */
                        ArrayList<Categoria> auxCopyCategorias = new ArrayList<>(pelicula3.getCategorias());
                        auxCopyCategorias.forEach(categoria -> {
                                categoria.getPeliculas().remove(pelicula3);
                                pelicula3.getCategorias().remove(categoria);
                        });
                        //SE PUEDE DESVINCULAR EL TAG DEL MANYTOMANY DADO QUE
                        //NO ES EL PROPIETARIO, EN ESTE CASO EL PROPIETARIO
                        //DE LA RELACION ES POST
                        peliculaRepository.save(pelicula3);
                }

                @Test
                @Order(4)
                void borrarPelicula() {

                        Pelicula pelicula3 = peliculaRepository.findById(3L).orElse(null);
                        //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
                        //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
       /* List<Categoria> categorias = entityManager.createQuery(
                        "select p " +
                                "from Categoria p " +
                                "join fetch p.peliculas ts " +
                                "where ts.id = :id", Categoria.class)
                .setParameter("id", pelicula.getId())
                .getResultList();
        pelicula.setCategorias(new HashSet<>(categorias));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Categoria.class,
                                Pelicula.class,
                                pelicula3.getId(),
                                pelicula3::setCategorias
                        );*/


                        ArrayList<Categoria> auxCopyCategorias = new ArrayList<>(pelicula3.getCategorias());
                        auxCopyCategorias.forEach(categoria -> {
                                categoria.getPeliculas().remove(pelicula3);
                                pelicula3.getCategorias().remove(categoria);
                        });
                        //SE PUEDE DESVINCULAR EL TAG DEL MANYTOMANY DADO QUE
                        //NO ES EL PROPIETARIO, EN ESTE CASO EL PROPIETARIO
                        //DE LA RELACION ES POST
                        peliculaRepository.delete(pelicula3);

                }

}



