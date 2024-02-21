package org.iesvdm.videoclub.repository;

import org.iesvdm.videoclub.domain.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio, String> {
}
