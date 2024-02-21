package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Actor;
import org.iesvdm.videoclub.exception.ActorNotFoundException;
import org.iesvdm.videoclub.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {
    
    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> all() {
        return this.actorRepository.findAll();
    }

    public Actor save(Actor actor) {

        return this.actorRepository.save(actor);
    }

    public Actor one(Long id) {
        return this.actorRepository.findById(id)
                .orElseThrow(() -> new ActorNotFoundException(id));
    }

    public Actor replace(Long id, Actor actor) {

        return this.actorRepository.findById(id)
                .map( a -> (id.equals(actor.getId()) ?
                        this.actorRepository.save(actor) : null))
                .orElseThrow(() -> new ActorNotFoundException(id));

    }

    public void delete(Long id) {
        this.actorRepository.findById(id).map(a -> {
                    this.actorRepository.delete(a);
                    return a;})
                .orElseThrow(() -> new ActorNotFoundException(id));
    }
}
