package org.iesvdm.videoclub;

import jakarta.transaction.Transactional;
import org.iesvdm.videoclub.domain.Comentario;
import org.iesvdm.videoclub.domain.Tutorial;
import org.iesvdm.videoclub.repository.ComentarioRepository;
import org.iesvdm.videoclub.repository.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@SpringBootTest
class VideoclubApplicationTests {

    @Autowired
    TutorialRepository tutorialRepository;

    @Autowired
    ComentarioRepository comentarioRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void pruebaOneToManyTutorial(){
        var tutorialList = tutorialRepository.findAll();

        Tutorial tutorial = new Tutorial(-1,"Tutorial", "tutorial",true,new Date(),new HashSet<>());
        tutorial.setComentarios(new HashSet<>());

        Comentario comentario1 = new Comentario(0,"Este es el primer comentario",tutorial);
        Comentario comentario2 = new Comentario(-1,"Este es el segundo comentario",tutorial);

        tutorial.getComentarios().add(comentario1);
        tutorial.getComentarios().add(comentario2);

        tutorialList.forEach(System.out::println);

        tutorialRepository.save(tutorial);


    }

    @Test
    @Transactional
    void pruebaGrabarOneToManyTutorial(){
        // *********** SIN @RANSACTIONAL ******************
        /*Tutorial tutorial2 = new Tutorial(-1,"Tutorial2","tutorial2", true, new Date(),new HashSet<>());

        Comentario comentario3 = new Comentario(0,"Este es el tercer comentario",tutorial2);
        Comentario comentario4 = new Comentario(-1,"Este es el cuarto comentario",tutorial2);

        comentario3.setTexto("comentario 3");
        comentario3.setTutorial(tutorial2);
        //tutorialRepository.flush();


        comentario4.setTexto("comentario 4");
        comentario4.setTutorial(tutorial2);

        tutorial2.addComentario(comentario3).addComentario(comentario4);
        tutorialRepository.save(tutorial2);
        tutorialRepository.flush();*/

        //***********************************************************
        // ********** CON @TRANSACTIONAL **************
       Tutorial tutorial3 = Tutorial.builder()
               .titulo("Tutorial3")
               .publicado(true)
               .fechaPublicacion(new Date())
               .descripcion("tutorial3")
               .comentarios(new HashSet<>())
               .build();
        tutorialRepository.save(tutorial3);
        tutorialRepository.flush();

       Comentario comentario5 = Comentario.builder()
               .texto("coment-5")
               .build();
        comentario5.setTexto("comen-5");
        comentario5.setTutorial(tutorial3);
        comentarioRepository.save(comentario5);
        comentarioRepository.flush();

       Comentario comentario6 = Comentario.builder()
               .texto("coment-6")
               .build();
        comentario6.setTexto("coment-6");
        comentario6.setTutorial(tutorial3);
       comentarioRepository.save(comentario6);
       comentarioRepository.flush();

       tutorial3.addComentario(comentario5).addComentario(comentario6);
       tutorialRepository.save(tutorial3);
       tutorialRepository.flush();

    }

   /* @Test
    @Transactional
    void pruebaEliminarOneToManyComentarios(){

        //Tutorial tutorial = new Tutorial(0,"Tutorial","tutorial", true, new Date(),new HashSet<>());

        var optionalTutorial  = this.tutorialRepository.findById(1l);

        optionalTutorial.ifPresent(tutorial ->
                tutorial
                .getComentarios()
                .forEach(System.out::println));


        var optionalComentario = tutorial.getComentarios().stream.findFirst();

        tutorial.removeComentario(optionalComentario.get());

    }

    @Test
    @Transactional
    void pruebaEliminarOneToManyTutorial(){

        var optionalTutorial  = this.tutorialRepository.findById(1l);

        this.
    }*/
}
