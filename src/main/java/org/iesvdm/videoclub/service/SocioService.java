package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Socio;
import org.iesvdm.videoclub.exception.SocioNotFoundException;
import org.iesvdm.videoclub.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocioService {
    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    public List<Socio> all() {
        return this.socioRepository.findAll();
    }

    public Socio save(Socio socio) {

        return this.socioRepository.save(socio);
    }

    public Socio one(String dni) {
        return this.socioRepository.findById(dni)
                .orElseThrow(() -> new SocioNotFoundException(dni));
    }

    public Socio replace(String dni, Socio socio) {

        return this.socioRepository.findById(dni)
                .map( s -> (dni.equals(s.getDni()) ?
                        this.socioRepository.save(socio) : null))
                .orElseThrow(() -> new SocioNotFoundException(dni));

    }

    public void delete(String dni) {
        this.socioRepository.findById(dni).map(a -> {
                    this.socioRepository.delete(a);
                    return a;})
                .orElseThrow(() -> new SocioNotFoundException(dni));
    }
}
