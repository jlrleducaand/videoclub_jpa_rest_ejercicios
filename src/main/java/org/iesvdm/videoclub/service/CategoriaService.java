package org.iesvdm.videoclub.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.dto.CategoriaDtoRepository;
import org.iesvdm.videoclub.exception.CategoriaNotFoundException;
import org.iesvdm.videoclub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements CategoriaDtoRepository{

    private final CategoriaRepository categoriaRepository;
    private final PeliculaService peliculaRepository;


    @PersistenceContext
    EntityManager em;


    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository
                            , PeliculaService peliculaRepository){

        this.categoriaRepository = categoriaRepository;
        this.peliculaRepository = peliculaRepository;
    }

    public List<Categoria> all() {

        return this.categoriaRepository.findAll();
    }

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
        return this.categoriaRepository.findById(id).map( p -> (id.equals(categoria.getId())  ?
                        this.categoriaRepository.save(categoria) : null))
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    public void delete(Long id) {
        this.categoriaRepository.findById(id).map(p -> {this.categoriaRepository.delete(p);
                    return p;})
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    // ******************   CALCULOS SOBRE LA BASE DE DATOS     ******************

    public Categoria addPeliculaACategoria(Long idPel, Long idCat){
        Pelicula p = peliculaRepository.one(idPel);
        Categoria c = one(idCat);
        if(!this.one(idCat).getPeliculas().contains(p)){
            c.getPeliculas().add(p);
            save(c);
        }

        return c;
    }


    // *****************    TRABAJANDO CON LA BASE DE DATOS     *******************

    //BLOQUE METHOD @Query JPQL CON OBJETOS DE ENTIDADES JPA
    //Notación para asociar peticiones JPQL o SQL a un método pasando parámetros por orden de entrada
    // de la firma del método o parametrizados con nombre
    public List<Categoria> queryCategoriaCustomJPQL(Optional<String> buscarOptional, Optional<String>  ordenarOptional) {
        StringBuilder queryBuilder = new StringBuilder("select C from categoria");
        if (buscarOptional.isPresent()){
            queryBuilder.append(" ").append("where C.nombre like: nombre");
        }
        if (ordenarOptional.isPresent()){
            if(buscarOptional.isPresent() && "asc".equalsIgnoreCase(buscarOptional.get())){
                queryBuilder.append(" ").append("order by C.nombre ASC");
            }else if(buscarOptional.isPresent() && "desc".equalsIgnoreCase(buscarOptional.get())) {
                queryBuilder.append(" ").append("order by C.nombre desc");
            }
        }
        Query query = em.createQuery(queryBuilder.toString());
        if (buscarOptional.isPresent()){
            query.setParameter("nombre", "%"+buscarOptional.get()+"%");
        }
        return query.getResultList();
    }



    //BLOQUE DE MÉTODOS @QUERY CON "SQL" NATIVO BASADO EN LAS TABLAS.
    //@Query nativeQuery = true, es decir, SQL:
    // Se Parametrizan con el nombre del parámetro:  (%:nombre%)
    public List<Categoria> queryCategoriaCustomJPA(Optional<String> buscarOptional,Optional<String>  ordenarOptional ) {
        StringBuilder queryBuilder = new StringBuilder("select * from categoria");
        if (buscarOptional.isPresent()){
            queryBuilder.append(" ").append("where nombre like: nombre");
        }
        if (ordenarOptional.isPresent()){
            if(buscarOptional.isPresent() && "asc".equalsIgnoreCase(buscarOptional.get())){
                queryBuilder.append(" ").append("order by nombre ASC");
            }else if(buscarOptional.isPresent() && "desc".equalsIgnoreCase(buscarOptional.get())) {
                queryBuilder.append(" ").append("order by nombre desc");
            }
        }
        Query query = em.createNativeQuery(queryBuilder.toString(),Categoria.class);
        if (buscarOptional.isPresent()){
            query.setParameter("nombre", "%"+buscarOptional.get()+"%");
        }

        return query.getResultList();
    }

    @Override
    public int numPeliculasPorCategoria(Long idCat) {
        return this.one(idCat).getPeliculas().size();
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
