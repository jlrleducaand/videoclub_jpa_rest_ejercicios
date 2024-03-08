package org.iesvdm.videoclub.repository;

import org.iesvdm.videoclub.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM Categoria c WHERE c.nombre LIKE %:nombre%")
    List<Categoria> queryCategoriaCustomJPQL(@Param("nombre") Optional<String> buscarOptional);

    // Método para consulta SQL nativa
    @Query(nativeQuery = true, value = "SELECT * FROM Categoria WHERE nombre LIKE CONCAT('%', :nombre, '%')")
    default List<Categoria> queryCategoriaCustomJPA(@Param("nombre") Optional<String> buscarOptional) {
        return queryCategoriaCustomJPA(buscarOptional.orElse(null));
    }

    // Método auxiliar para consulta SQL nativa
    @Query(nativeQuery = true, value = "SELECT * FROM Categoria WHERE nombre LIKE CONCAT('%', :nombre, '%')")
    List<Categoria> queryCategoriaCustomJPA(String nombre);


    public List<Categoria> findAll();

    public List<Categoria> findByNombreContainsIgnoreCaseOrderByNombreAsc(String nombre);

    public List<Categoria> findByNombreContainsIgnoreCaseOrderByNombreDesc(String nombre);

    public List<Categoria> findByNombreContainsIgnoreCase(String nombre);

    public Page<Categoria> findByNombreContainsIgnoreCase(String nombre, Pageable pageable);

    public Page<Categoria> findAll(Pageable pageable);


}
