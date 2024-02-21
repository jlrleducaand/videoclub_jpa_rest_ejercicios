package org.iesvdm.videoclub.repository;

import org.iesvdm.videoclub.domain.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {


}
