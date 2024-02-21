package org.iesvdm.videoclub.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.videoclub.domain.Tutorial;
import org.iesvdm.videoclub.service.TutorialService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/tutoriales")
public class TutorialController {

    private final TutorialService tutorialService;

    public TutorialController(org.iesvdm.videoclub.service.TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }


    @GetMapping({"","/"})
    public List<Tutorial> all() {
        log.info("Accediendo a todas los comentarios");
        return this.tutorialService.all();
    }

    @PostMapping({"","/"})
    public Tutorial newTutorial(@RequestBody @Valid Tutorial tutorial) {
        return this.tutorialService.save(tutorial);
    }

    @GetMapping("/{id}")
    public Tutorial one(@PathVariable("id") Long id) {
        return this.tutorialService.one(id);
    }

    @PutMapping("/{id}")
    public Tutorial replaceTutorial(@PathVariable("id") Long id, @RequestBody @Valid  Tutorial tutorial) {
        return this.tutorialService.replace(id, tutorial);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTutorial(@PathVariable("id") Long id) {

        this.tutorialService.delete(id);
    }
}
