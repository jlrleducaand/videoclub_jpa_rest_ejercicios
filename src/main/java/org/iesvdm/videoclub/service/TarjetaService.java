package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Tarjeta;
import org.iesvdm.videoclub.exception.TarjetaNotFoundException;
import org.iesvdm.videoclub.repository.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarjetaService {

    private final TarjetaRepository tarjetaRepository;

    @Autowired
    public TarjetaService(TarjetaRepository tarjetaRepository) {
        this.tarjetaRepository = tarjetaRepository;
    }

    public List<Tarjeta> all(){
        return this.tarjetaRepository.findAll();
    }

    public Tarjeta save(Tarjeta tarjeta){
       return this.tarjetaRepository.save(tarjeta);
    }

    public Tarjeta one(Long id){
        return this.tarjetaRepository.findById(id)
                .orElseThrow(() -> new TarjetaNotFoundException(id));
    }

    public Tarjeta replace(Long id, Tarjeta tarjeta) {
        return this.tarjetaRepository.findById(id)
                .map(t -> (id.equals(t.getNumero()) ?
                        this.tarjetaRepository.save(tarjeta) : null))
                .orElseThrow(() -> new TarjetaNotFoundException(id));
    }

    public Tarjeta delete(Long id){
        return this.tarjetaRepository.findById(id).map(t -> {
                    this.tarjetaRepository.delete(t);
                    return t;})
                .orElseThrow(() -> new TarjetaNotFoundException(id));
    }

}
