package org.iesvdm.videoclub.repository;


import org.iesvdm.videoclub.domain.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Tutorial, Long> {
}
