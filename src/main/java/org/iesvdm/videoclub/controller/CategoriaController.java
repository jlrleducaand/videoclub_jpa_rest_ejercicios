package org.iesvdm.videoclub.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.service.CategoriaService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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


        @GetMapping(value = {"","/"}, params = {"!page", "!size", "!buscar", "!ordenar"})
        //opcion2  params={""} no funciona
        public List<Categoria> all() {
            categoriaService.PeliculasPorCategorias();
            log.info("Accediendo a todas las categorías");
            return this.categoriaService.all();
        }


        @GetMapping(value = {"", "/"}, params = {"page", "size", "sort"})
        // para los campos sort separar con coma (id,desc)
        public Page<Categoria> all(Pageable pageable) {
            categoriaService.PeliculasPorCategorias();
            log.info("Accediendo a todas las categorías paginadas");
            return this.categoriaService.getAll(pageable);
        }


        // Ruta Para el Uso de JPQL  // *** no funciona en insomnia no ordena  REVISAR ***
        /*@GetMapping(value = {"","/"}, params = {"buscar", "ordenar"})
        public List<Categoria> all(
                @RequestParam(value = "buscar", required = false) String buscar,
                @RequestParam(value = "ordenar", required = false) String ordenar)
        {
            Optional<String> buscarOptional = Optional.ofNullable(buscar);
            Optional<String> ordenarOptional = Optional.ofNullable(ordenar);

            log.info("Accediendo a todas las categorías con filtro buscar {}"
                    + "y ordenar {}  ",
            buscarOptional.orElse("VOID"),
            ordenarOptional.orElse("VOID"));
            return this.categoriaService.queryCategoriaCustomJPQL(buscarOptional,ordenarOptional);

        }*/

        // Ruta Para el uso de QueryAutoJPA
        @GetMapping(value = {"","/"}, params = {"buscar", "ordenar"})
        public List<Categoria> all(
                @RequestParam(value = "buscar", required = false) String buscar,
                @RequestParam(value = "ordenar", required = false) String ordenar)
        {
            Optional<String> buscarOptional = Optional.ofNullable(buscar);
            Optional<String> ordenarOptional = Optional.ofNullable(ordenar);

            log.info("Accediendo a todas las categorías con filtro buscar {}"
                            + "y ordenar {} probado en imsomnia ",
                    buscarOptional.orElse("VOID"),
                    ordenarOptional.orElse("VOID"));

            return this.categoriaService.findByNombreContainsIgnoreCaseOrderByNombre(buscarOptional.orElse(""), ordenarOptional.orElse(""));

        }
        // Configuracion manual
       /* @GetMapping(value = {"","/"}, params = {"pagina", "tamanio"})
        public ResponseEntity<Map<String,Object>> all(
                @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
                @RequestParam(value = "tamanio", required = false, defaultValue = "3") int tamanio)
        {
            Optional<Integer> paginaOptional = Optional.ofNullable(pagina);
            Optional<Integer> tamanioOptional = Optional.ofNullable(tamanio);

            log.info("Accediendo a todas las categorías con paginacion ");
                   Map<String, Object> responseAll = categoriaService.all(paginaOptional, tamanioOptional);
            return new ResponseEntity.ok(responseAll);

        }*/

        @PostMapping({"","/"})
        public Categoria newCategoria(@RequestBody @Valid  Categoria categoria) {
            log.info("Creando una Categoría"+ categoria.getNombre());
            return this.categoriaService.save(categoria);
        }

        @GetMapping("/{id}")
        public Categoria one(@PathVariable("id") Long id) {
            categoriaService.numPeliculasPorCategoria(id);
            log.info("Buscando una Categoria con id "+id);

            return this.categoriaService.one(id);
        }

        @PutMapping("/{id}")
        public Categoria replaceCategoria(@PathVariable("id") Long id, @RequestBody Categoria categoria) {
            log.info("Actualizando una categoría con id "+ id);
            return this.categoriaService.replace(id, categoria);
        }

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

}

