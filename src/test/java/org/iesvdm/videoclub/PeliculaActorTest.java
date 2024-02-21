package org.iesvdm.videoclub;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Actor;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.repository.ActorRepository;
import org.iesvdm.videoclub.repository.PeliculaRepository;
import org.iesvdm.videoclub.repository.CategoriaRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.HashSet;


    @SpringBootTest
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class PeliculaActorTest {

        @Autowired
        PeliculaRepository peliculaRepository;

        @Autowired
        CategoriaRepository categoriaRepository;

        @Autowired
        ActorRepository actorRepository;

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
            Actor actor1 = new Actor(0, "Actor1", new HashSet<Pelicula>());
            actorRepository.save(actor1);

            Actor actor2 = new Actor(0, "actor2", new HashSet<Pelicula>());
            actorRepository.save(actor2);

            Pelicula pelicula1 = new Pelicula(0, "Pelicula1", new HashSet<Categoria>(), new HashSet<Actor>());
            peliculaRepository.save(pelicula1);


            Pelicula pelicula2 = new Pelicula(0, "Pelicula2", new HashSet<Categoria>(), new HashSet<Actor>());
            peliculaRepository.save(pelicula2);

            Actor actor3 = new Actor(0, "Actor3 - Programando fácil con JPA :P", new HashSet<>());
            actorRepository.save(actor3);
            actor3.getPeliculas().add(pelicula1);
            //RECUERDA QUE LA COLECCIÓN DE CATEGORIAS ES UN SET Y NO PUEDE HABER REPETIDOS CON EL
            //MISMO ID por eso se graba cada uno

            pelicula1.getActores().add(actor3);
            peliculaRepository.save(pelicula1);

            Actor Actor2 = new Actor(0, "Actor2 - Programando fácil2 con JPA :P", new HashSet<>());

            Actor2.getPeliculas().add(pelicula2);
            actorRepository.save(Actor2);

            pelicula2.getActores().add(Actor2);
            peliculaRepository.save(pelicula2);

        }




        @Test
        @Order(2)
        void grabarPeliculaQueYaExiste() {

            Pelicula pelicula3 = new Pelicula(0, "EEEH Pelicula 3!!!!", new HashSet<>());
            peliculaRepository.save(pelicula3);

            Actor actor2 = new Actor(0, "Actor2 - NO programando tan fácilmente...", new HashSet<Pelicula>());
            actorRepository.save(actor2);

            //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
            //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                       /* List<Pelicula> peliculas = entityManager.createQuery(
                       "select t " +
                               "from Pelicula t " +
                               "join fetch t.Actors ps " +
                               "where ps.id = :id", Pelicula.class)
                                .setParameter("id", Actor2.getId())
                                .getResultList();

                        Actor2.setPeliculas(new HashSet<>(peliculas));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Pelicula.class,
                                Categoria.class,
                                Actor2.getId(),
                                Actor2::setPeliculas
                        );*/


            Pelicula pelicula1 = peliculaRepository.findById(1L).orElse(null);
            //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
            //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                       /* List<Categoria> Actors = entityManager.createQuery(
                                "select p " +
                                        "from Categoria p " +
                                        "join fetch p.peliculas ts " +
                                        "where ts.id = :id", Categoria.class)
                                .setParameter("id", pelicula1.getId())
                                .getResultList();
                        pelicula1.setCategorias(new HashSet<>(Actors));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Categoria.class,
                                Pelicula.class,
                                pelicula1.getId(),
                                pelicula1::setCategorias
                        );*/

            actor2.getPeliculas().add(pelicula1);
            actor2.getPeliculas().add(pelicula3);
            actorRepository.save(actor2);
        }


        @Test
        @Order(3)
        void desasociarPelicula() {

            Pelicula pelicula4 = peliculaRepository.findById(4L).orElse(null);
            //Si se utlizas un fetch LAZY, la mejor estrategia es realizar un join fetch en JPQL
            //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
                        /*List<Categoria> Actors = entityManager.createQuery(
                        "select p " +
                                "from Categoria p " +
                                "join fetch p.peliculas ts " +
                                "where ts.id = :id", Categoria.class)
                                .setParameter("id", pelicula.getId())
                                .getResultList();

                        pelicula.setCategorias(new HashSet<>(Actors));
                                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                                Categoria.class,
                                                Pelicula.class,
                                                pelicula1.getId(),
                                                pelicula1::setCategorias
                        );
                        */
            ArrayList<Actor> auxCopyActores = new ArrayList<>(pelicula4.getActores());
            auxCopyActores.forEach(Actor -> {
                Actor.getPeliculas().remove(pelicula4);
                pelicula4.getActores().remove(Actor);
            });
            //SE PUEDE DESVINCULAR EL TAG DEL MANYTOMANY DADO QUE
            //NO ES EL PROPIETARIO, EN ESTE CASO EL PROPIETARIO
            //DE LA RELACION ES POST
            peliculaRepository.save(pelicula4);
        }

        @Test
        @Order(4)
        void borrarPelicula() {

            Pelicula pelicula4 = peliculaRepository.findById(4L).orElse(null);
            //Si se utlizas un fetch LAZY, mejor estrategia realizar un join fetch en JPQL
            //y cargar en la colección. NOTA: si utilizas EAGER puedes prescindir de join fetch.
       /* List<Categoria> Actors = entityManager.createQuery(
                        "select p " +
                                "from Categoria p " +
                                "join fetch p.peliculas ts " +
                                "where ts.id = :id", Categoria.class)
                .setParameter("id", pelicula.getId())
                .getResultList();
        pelicula.setCategorias(new HashSet<>(Actors));
                        UtilJPA.initializeLazyManyToManyByJoinFetch(entityManager,
                                Categoria.class,
                                Pelicula.class,
                                pelicula3.getId(),
                                pelicula3::setCategorias
                        );*/


            ArrayList<Actor> auxCopyActores = new ArrayList<>(pelicula4.getActores());
            auxCopyActores.forEach(actor -> {
                actor.getPeliculas().remove(pelicula4);
                actorRepository.save(actor);
                pelicula4.getActores().remove(actor);
            });

            ArrayList<Categoria> auxCopyCategorias = new ArrayList<>(pelicula4.getCategorias());
            auxCopyCategorias.forEach(cat -> {
                cat.getPeliculas().remove(pelicula4);
                categoriaRepository.save(cat);
                pelicula4.getCategorias().remove(cat);
            });
            peliculaRepository.save(pelicula4);

            //SE PUEDE DESVINCULAR EL TAG DEL MANYTOMANY DADO QUE
            //NO ES EL PROPIETARIO, EN ESTE CASO EL PROPIETARIO
            //DE LA RELACION ES POST

            peliculaRepository.delete(pelicula4);

        }

    }
    

