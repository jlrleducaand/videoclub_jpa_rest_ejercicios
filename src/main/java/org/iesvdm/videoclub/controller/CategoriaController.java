package org.iesvdm.videoclub.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


    @Slf4j
    @RestController
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/categorias")
    public class CategoriaController {

        private final CategoriaService categoriaService;

        @Autowired
        public CategoriaController(CategoriaService categoriaService) {

            this.categoriaService = categoriaService;
        }

        @GetMapping(value = {"","/"}, params = {"!buscar", "!ordenar"})
        public List<Categoria> all() {
            log.info("Accediendo a todas las categorías");
            return this.categoriaService.all();
        }

        @GetMapping(value = {"","/"}, params = {"buscar", "ordenar"})
        public List<Categoria> all(
                @RequestParam(value = "buscar", required = false) String buscar,
                @RequestParam(value = "ordenar", required = false) String ordenar)
        {
            Optional<String> buscarOptional = Optional.ofNullable(buscar);
            Optional<String> ordenarOptional = Optional.ofNullable(ordenar);

            log.info("Accediendo a todas las categorías con filtro buscar {}"
                    + "y ordenar {} ",
            buscarOptional.orElse("VOID"),
            ordenarOptional.orElse("VOID"));
            return this.categoriaService.queryCategoriaCustomJPQL(buscarOptional,ordenarOptional);

        }

        @PostMapping({"","/"})
        public Categoria newCategoria(@RequestBody @Valid  Categoria categoria) {
            log.info("Creando una Categoría"+ categoria.getNombre());
            return this.categoriaService.save(categoria);
        }

        @GetMapping("/{id}")
        public Categoria one(@PathVariable("id") Long id) {
            log.info("Buscando una Categoria con id "+id);
            return this.categoriaService.one(id);
        }

        @PutMapping("/{id}")
        public Categoria replaceCategoria(@PathVariable("id") Long id, @RequestBody Categoria categoria) {
            log.info("Actualizando una categoría con id "+ id);
            return this.categoriaService.replace(id, categoria);
        }

        @ResponseBody
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @DeleteMapping({"{id}","/{id}"})
        public void deleteCategoria(@PathVariable("id") Long id) {
            log.info("Eliminando una categoría con id "+id);
            this.categoriaService.delete(id);
        }



        @PutMapping(value ={"/{idCat}/addpel/{idPel}","/{idCat}/addpel/{idPel}/"})
        public Categoria addPeliculaACategoria(@PathVariable Long idPel, @PathVariable Long idCat) {
            Categoria c = this.categoriaService.addPeliculaACategoria(idPel,idCat);

            log.info("Añadiendo pelicula con id "+ idPel +" al set de categoria-peliculas  la categoria con id "+idCat );
            return c;
        }


       /* @GetMapping("/dto-all")
        public List<CategoriaDTO> getAllCategoriasDTO(){
            log.info("Accediendo a todas las categorias con campos extendidos");
            return categoriaService.getCategoriasDTO();
        }

        @GetMapping("/dto-{id}")
        public CategoriaDTO oneDTO(@PathVariable("id") Long id) {
            log.info("Accediendo a categoría con campo extra & id "+id);
            return this.categoriaService.oneDTO(id);
        }*/

}

