package org.iesvdm.videoclub.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.dto.CategoriaDTO;
import org.iesvdm.videoclub.exception.CategoriaNotFoundException;
import org.iesvdm.videoclub.repository.*;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class CategoriaService  {

    private final CategoriaRepository categoriaRepository;
    private final PeliculaService peliculaService;


    // para las consultas dinamicas  8 en una  logica de control para multiconsultas
    @PersistenceContext
    EntityManager em;



    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository
                            , PeliculaService peliculaService){

        this.categoriaRepository = categoriaRepository;
        this.peliculaService = peliculaService;
    }


    public List<Categoria> all() {
        return this.categoriaRepository.findAll();
    }

    public Page<Categoria> getAll(Pageable pageable) {
        Page<Categoria> pageCat = this.categoriaRepository.findAll(pageable);

        return pageCat;
    }
   /* public Page<Categoria> all46(Pageable pageable, Optional<String> columnOptional, Optional<String> sortOptional) {
            pageable.getSort()

            }
        }
        Page<Categoria> pageCat = this.categoriaRepository.findAll(pageable);

    }*/

    // AUTOMATICA PARA DOS CAMPOS CON DIFERENTES DEVOLUCIONES PAGE Y LIST
    public Page<Categoria> getAllBuscar(String buscar, Pageable pageable) {
        return this.categoriaRepository.findByNombreContainsIgnoreCase(buscar, pageable);
    }

    public Map<String, Object> procesarOrden(String campo, String direccion) {

        Pageable pageable = (direccion.equals("desc")) ?
                PageRequest.of(0, 20, Sort.by(campo).descending()) :
                PageRequest.of(0, 20, Sort.by(campo).ascending());

        Page<Categoria> pageAll = this.categoriaRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("categorias",pageAll.getContent());
        response.put("currentPage",pageAll.getNumber());
        response.put("totalItems",pageAll.getTotalElements());
        response.put("totalPages",pageAll.getTotalPages());

        return response;
    }
    public Map<String, Object> procesarOrden2(String campo1, String direccion1, String campo2, String direccion2) {

        Sort sort = Sort.by(
                Sort.Order.by(campo1).with(
                        direccion1.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC
                ),
                Sort.Order.by(campo2).with(
                        direccion2.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC
                )
        );

        Pageable pageable = PageRequest.of(0, 20,sort);


        Page<Categoria> pageAll = this.categoriaRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("categorias",pageAll.getContent());
        response.put("currentPage",pageAll.getNumber());
        response.put("totalItems",pageAll.getTotalElements());
        response.put("totalPages",pageAll.getTotalPages());


        return response;
    }

    // MANUAL PARA DOS CAMPOS CON DIFERENTES DEVOLUCIONES PAGE Y LIST
    /*public Page<Categoria> getAllBuscar( String buscar, Pageable pageable) {
        Page<Categoria> pageCat = this.categoriaRepository.findAll(pageable);
        List<Categoria> listCat = this.categoriaRepository.findByNombreContainsIgnoreCase(buscar);

        // Concatenar las dos listas en un Set
        Set<Categoria> mergedSet = Stream.concat(pageCat.getContent().stream(), listCat.stream())
                .collect(Collectors.toSet());

        // Convertir el HashSet en una lista nuevamente
        List<Categoria> mergedList = mergedSet.stream().collect(Collectors.toList());


        // Crear un nuevo objeto Page a partir de la lista combinada
        Page<Categoria> mergedPage = new PageImpl<>(mergedList, pageable, mergedList.size());

        return mergedPage;
    }*/

    @Transactional
    public Categoria save(Categoria categoria) {
        this.categoriaRepository.save(categoria);
        this.em.refresh(categoria);

        return categoria;
    }

    public Categoria one(Long id) {
        return this.categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    public Categoria replace(Long id, Categoria categoria) {
        return this.categoriaRepository.findById(id).map( c -> (id.equals(categoria.getId())  ?
                        this.categoriaRepository.save(categoria) : null))
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    public void delete(Long id) {
        log.info("Eliminando la categoría con ID: " + id);
        Categoria cat = this.categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));

        Set<Pelicula> peliculas = cat.getPeliculas();
        // Eliminar las películas asociadas una por una
        for (Pelicula pelicula : peliculas) {
            peliculaService.delete(pelicula.getId());
        }

        this.categoriaRepository.findById(id).map(c -> {

            this.categoriaRepository.delete(c);
                    return c;})
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    // ******************   CALCULOS SOBRE LA BASE DE DATOS     ******************

    public Categoria addPeliculaACategoria(Long idPel, Long idCat){
        Pelicula p = peliculaService.one(idPel);
        Categoria c = one(idCat);
        if(!this.one(idCat).getPeliculas().contains(p)){
            c.getPeliculas().add(p);
            save(c);
            peliculasPorCategorias();
        }

        return c;
    }
    public int numPeliculasPorCategoria(Long id) {
        Categoria cat = one(id);
        int cont = cat.getPeliculas().size();
        if  (cont > 0) {
               cat.setNumPelis(cont);
            }else{
                cat.setNumPelis(0);
                cont = 0;
            }
        return cont;
        }


    public void peliculasPorCategorias() {
        List<Categoria> cats = categoriaRepository.findAll();
        int cont = 0;
        for (Categoria cat : cats) {
            cont = (cat.getPeliculas().size());
            if (cont >0) {
                cat.setNumPelis(cont);
            }else{
                cat.setNumPelis(0);
            }
        }
    }


    // *****************    TRABAJANDO CON LA BASE DE DATOS     *******************

    //BLOQUE METHOD @Query JPQL CON OBJETOS DE ENTIDADES JPA
    //Notación para asociar peticiones JPQL o SQL a un método pasando parámetros por orden de entrada
    // de la firma del método o parametrizados con nombre
    public List<Categoria> queryCategoriaCustomJPQL(Optional<String> buscarOptional, Optional<String>  ordenarOptional) {
        String queryBodyString = "select C from Categoria as C";  //cuerpo repetitivo
        if (buscarOptional.isPresent()){
            queryBodyString += " where C.nombre like :nombre";
        }
        if (ordenarOptional.isPresent()){
            if(buscarOptional.isPresent() && "asc".equalsIgnoreCase(buscarOptional.get())){
                queryBodyString += " order by C.nombre ASC";
            }else if(buscarOptional.isPresent() && "desc".equalsIgnoreCase(buscarOptional.get())) {
                queryBodyString += " order by C.nombre desc";
            }
        }
        Query query = em.createQuery(queryBodyString.toString());
        if (buscarOptional.isPresent()){
            query.setParameter("nombre", "%"+buscarOptional.get()+"%");
        }
        return query.getResultList();
    }



    //BLOQUE DE MÉTODOS @QUERY CON "SQL" NATIVO BASADO EN LAS TABLAS.
    //@Query nativeQuery = true, es decir, SQL:
    // Se Parametrizan con el nombre del parámetro:  (%:nombre%)
    public List<Categoria> queryCategoriaCustomJPA(Optional<String> buscarOptional,Optional<String>  ordenarOptional ) {
        String queryBodyString = "select * from categoria";
        if (buscarOptional.isPresent()){
            queryBodyString += "where nombre like :nombre";
        }
        if (ordenarOptional.isPresent()){
            if(buscarOptional.isPresent() && "asc".equalsIgnoreCase(buscarOptional.get())){
                queryBodyString += "order by nombre ASC";
            }else if(buscarOptional.isPresent() && "desc".equalsIgnoreCase(buscarOptional.get())) {
                queryBodyString += "order by nombre desc";
            }
        }
        Query query = em.createNativeQuery(queryBodyString.toString(),Categoria.class);
        if (buscarOptional.isPresent()){
            query.setParameter("nombre", "%"+buscarOptional.get()+"%");
        }

        return query.getResultList();
    }

    //Bloque con Query de JPA auto
    public List<Categoria> findByNombreContainsIgnoreCaseOrderByNombre(String nombre, String orden){
        List<Categoria> lista = null;
        if(orden.equalsIgnoreCase("asc")){
            lista = categoriaRepository.findByNombreContainsIgnoreCaseOrderByNombreAsc(nombre);

        }else if(orden.equalsIgnoreCase("desc")){
            lista = categoriaRepository.findByNombreContainsIgnoreCaseOrderByNombreDesc(nombre);
        }
        return lista;
    }

    public List<Categoria> findByNombreContainsIgnoreCase(String nombre){
        List<Categoria> lista = null;
        return categoriaRepository.findByNombreContainsIgnoreCase(nombre);

    }







/*

    public List<CategoriaDTO> getCategoriasDTO() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(this::convertToCategoriaDTO)
                .collect(Collectors.toList());
    }

    private CategoriaDTO convertToCategoriaDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setPeliculas(categoria.getPeliculas());
        return categoriaDTO;
    }

    public CategoriaDTO oneDTO(Long id){
        return this.categoriaDTORepository.findById(id)
                .orElseThrow(() -> new CategoriaDTONotFoundException(id));
    }*/


}
