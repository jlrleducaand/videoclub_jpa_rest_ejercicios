package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Comentario;
import org.iesvdm.videoclub.domain.Tutorial;
import org.iesvdm.videoclub.repository.ComentarioRepository;
import org.iesvdm.videoclub.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorialService {


    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public void crearComentariosParaTutorial(Long tutorialId) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId).orElseThrow(() -> new RuntimeException("Tutorial no encontrado"));

        Comentario comentario1 = new Comentario();
        comentario1.setTexto("Este es el primer comentario");
        comentario1.setTutorial(tutorial);

        Comentario comentario2 = new Comentario();
        comentario2.setTexto("Este es el segundo comentario");
        comentario2.setTutorial(tutorial);

        tutorial.getComentarios().add(comentario1);
        tutorial.getComentarios().add(comentario2);

        tutorialRepository.save(tutorial);
    }

}
