package org.iesvdm.videoclub.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.videoclub.domain.Tarjeta;
import org.iesvdm.videoclub.domain.Tutorial;
import org.iesvdm.videoclub.service.TarjetaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/tarjetas")
public class TarjetaController {

    private final TarjetaService tarjetaService;

    public TarjetaController(TarjetaService tarjetaService) {
        this.tarjetaService = tarjetaService;
    }

    @GetMapping({"","/"})
    public List<Tarjeta> all() {
        log.info("Accediendo a todas las tarjetas");
        return this.tarjetaService.all();
    }

    @PostMapping({"","/"})
    public Tarjeta newTarjeta(@RequestBody @Valid Tarjeta tarjeta) {
        return this.tarjetaService.save(tarjeta);
    }

    @GetMapping("/{id}")
    public Tarjeta one(@PathVariable("id") Long id) {
        return this.tarjetaService.one(id);
    }

    @PutMapping("/{id}")
    public Tarjeta replaceTutorial(@PathVariable("id") Long id, @RequestBody @Valid  Tarjeta tarjeta) {
        return this.tarjetaService.replace(id, tarjeta);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTutorial(@PathVariable("id") Long id) {

        this.tarjetaService.delete(id);
    }


}
