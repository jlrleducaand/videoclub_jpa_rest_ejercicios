package org.iesvdm.videoclub.dto;

import org.springframework.stereotype.Repository;


@Repository
public interface CategoriaDtoRepository{


      int numPeliculasPorCategoria(Long idCat);


}
