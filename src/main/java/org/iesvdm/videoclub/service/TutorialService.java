package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Tutorial;
import org.iesvdm.videoclub.exception.TutorialNotFoundException;
import org.iesvdm.videoclub.repository.TutorialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorialService {

        private final TutorialRepository tutorialRepository;

        public TutorialService(TutorialRepository tutorialRepository) {
            this.tutorialRepository = tutorialRepository;
        }

        public List<Tutorial> all() {
            return this.tutorialRepository.findAll();
        }

        public Tutorial save(Tutorial tutorial) {
            return this.tutorialRepository.save(tutorial);
        }

        public Tutorial one(Long id) {
            return this.tutorialRepository.findById(id)
                    .orElseThrow(() -> new TutorialNotFoundException(id));
        }

        public Tutorial replace(Long id, Tutorial tutorial) {

            return this.tutorialRepository.findById(id)
                    .map( p -> (id.equals(tutorial.getId()) ?
                            this.tutorialRepository.save(tutorial) : null))
                    .orElseThrow(() -> new TutorialNotFoundException(id));

        }

        public void delete(Long id) {
            this.tutorialRepository.findById(id).map(p -> {
                        this.tutorialRepository.delete(p);
                        return p;})
                    .orElseThrow(() -> new TutorialNotFoundException(id));
        }

}


