package org.iesvdm.videoclub.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.videoclub.domain.Comentario;
import org.iesvdm.videoclub.service.ComentarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {

        this.comentarioService = comentarioService;
    }

    @GetMapping({"","/"})
    public List<Comentario> all() {
        log.info("Accediendo a todas los comentarios");
        return this.comentarioService.all();
    }

    @PostMapping({"","/"})
    public Comentario newComentario(@RequestBody @Valid Comentario comentario) {
        return this.comentarioService.save(comentario);
    }

    @GetMapping("/{id}")
    public Comentario one(@PathVariable("id") Long id) {
        return this.comentarioService.one(id);
    }

    @PutMapping("/{id}")
    public Comentario replaceComentario(@PathVariable("id") Long id, @RequestBody @Valid  Comentario comentario) {
        return this.comentarioService.replace(id, comentario);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteComentario(@PathVariable("id") Long id) {

        this.comentarioService.delete(id);
    }


}
