package org.iesvdm.videoclub;

import jakarta.transaction.Transactional;
import org.iesvdm.videoclub.domain.Comentario;
import org.iesvdm.videoclub.domain.Tutorial;
import org.iesvdm.videoclub.repository.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VideoclubApplicationTests {

    @Autowired
    TutorialRepository tutorialRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    void pruebaOneToManyTutorial(){
        var tutorialList = tutorialRepository.findAll();

        Tutorial tutorial = new Tutorial();
        Comentario comentario1 = new Comentario();
        Comentario comentario2 = new Comentario();


        comentario1.setTexto("Este es el primer comentario");
        comentario1.setTutorial(tutorial);

        comentario2.setTexto("Este es el segundo comentario");
        comentario2.setTutorial(tutorial);

        tutorial.getComentarios().add(comentario1);
        tutorial.getComentarios().add(comentario2);

        tutorialList.forEach(t -> System.out.println(t));
    }

}
