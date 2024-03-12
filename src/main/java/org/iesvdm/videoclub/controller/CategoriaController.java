package org.iesvdm.videoclub.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.service.CategoriaService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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


        @GetMapping(value = {"","/"}, params = {"!page", "!size", "!buscar", "!sort", "!column", "!orden", "!orden_"})
        //opcion2  params={""} no funciona
        public List<Categoria> all() {
            categoriaService.peliculasPorCategorias();
            log.info("Accediendo a todas las categorías sin parametros");
            return this.categoriaService.all();
        }


        @GetMapping(value = {"", "/"}, params = {"page", "size", "sort"})
        // para los campos sort separar con coma (id, desc)
        public Page<Categoria> all(Pageable pageable) {
            categoriaService.peliculasPorCategorias();
            log.info("Accediendo a todas las categorías paginadas 3 parametros: page size sort");
            return this.categoriaService.getAll(pageable);
        }


        // Ruta Para el Uso de JPQL  // *** no funcionó en insomnia no ordena  REVISAR ***
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
        @GetMapping(value = {"","/"}, params = {"buscar", "page", "size", "sort"})
        public Page<Categoria> all(Pageable pageable,
                @RequestParam(value = "buscar", required = false) String buscar)
        {
            Optional<String> buscarOptional = Optional.ofNullable(buscar);

            log.info("Accediendo a todas las categorías con 4 atributos: buscar page size sort");
            //devolucion al cliente
            return this.categoriaService.getAllBuscar(buscar, pageable);

        }


    @GetMapping(value = {"","/"}, params = {"orden"})
    public ResponseEntity<Map<String,Object>> all(
            @RequestParam(value = "orden", required = false, defaultValue = "id, asc" ) String[] orden
    ){
        log.info("Orden recibido en el controlador orden1 y orden2:" + orden);
        Map<String, Object> responseAll = null;
        String[] ordenSplited_0 = orden[0].split(",");
        String[] ordenSplited_1 = orden[1].split(",");
// Calcular la longitud total del nuevo array
        int totalLength = ordenSplited_0.length + ordenSplited_1.length;
// Crear un nuevo array con la longitud total
        String[] ordenSplitedRes = new String[totalLength];
// Copiar los elementos del primer array
        System.arraycopy(ordenSplited_0, 0, ordenSplitedRes, 0, ordenSplited_0.length);
// Copiar los elementos del segundo array
        System.arraycopy(ordenSplited_1, 0, ordenSplitedRes, ordenSplited_0.length, ordenSplited_1.length);
        // Verificar si se recibió una ordenación de dos niveles (campo, sentido, campo2, sentido2)
        if (ordenSplitedRes.length == 4) {
            log.info("Orden:" + orden);
            log.info("Orden recibido en el controlador dos orden:" + Arrays.toString(orden)+ ordenSplitedRes.length);
            String campo1 = ordenSplitedRes[0];
            String sentido1 = ordenSplitedRes[1];
            String campo2 = ordenSplitedRes[2];
            String sentido2 = ordenSplitedRes[3];
            responseAll = categoriaService.procesarOrden2(campo1, sentido1, campo2, sentido2);
            return ResponseEntity.ok(responseAll);

            // Verificar si se recibió una ordenación de un nivel (campo1, sentido1)
        }else if (ordenSplitedRes.length == 2){
            log.info("Orden:" + orden);
            log.info("Orden recibido en el controlador un orden:" + Arrays.toString(ordenSplitedRes) + ordenSplitedRes.length);
            String campo1 = ordenSplitedRes[0];
            String sentido1 = ordenSplitedRes[1];
            responseAll = categoriaService.procesarOrden(campo1, sentido1);
            return ResponseEntity.ok(responseAll);
        }
        // Manejar caso de error o formato incorrecto de la solicitud
        else {
            return ResponseEntity.badRequest().build();
        }
    }






    // Configuracion manual ejercicio_4_5
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
            this.categoriaService.peliculasPorCategorias();
            log.info("Añadiendo pelicula con id "+ idPel +" al set de categoria-peliculas  la categoria con id "+idCat );
            return c;
        }

}

