package org.iesvdm.videoclub.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.videoclub.domain.Socio;
import org.iesvdm.videoclub.service.SocioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/socios")
public class SocioController {   

        private final SocioService socioService;

        public SocioController(SocioService socioService) {

            this.socioService = socioService;
        }

        @GetMapping({"","/"})
        public List<Socio> all() {
            log.info("Accediendo a todas las socio es");
            return this.socioService.all();
        }

        @PostMapping({"","/"})
        public Socio newActor(@RequestBody Socio socio ) {
            return this.socioService.save(socio );
        }

        @GetMapping("/{id}")
        public Socio one(@PathVariable("id") String dni) {
            return this.socioService.one(dni);
        }

        @PutMapping("/{id}")
        public Socio replaceActor(@PathVariable("id") String dni, @RequestBody Socio socio ) {
            return this.socioService.replace(dni, socio );
        }


        @ResponseBody
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @DeleteMapping("/{id}")
        public void deleteActor(@PathVariable("id") String dni) {
            this.socioService.delete(dni);
        }
}

