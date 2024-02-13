package org.iesvdm.videoclub.exception;

public class TutorialNotFoundException extends RuntimeException {
    public TutorialNotFoundException(Long id) {
        super("Not found Tutorial with id: " + id);
    }
}
