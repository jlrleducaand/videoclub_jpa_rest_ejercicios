package org.iesvdm.videoclub.exception;

public class ComentarioNotFoundException extends RuntimeException{
    public ComentarioNotFoundException(Long id) {
        super("Not found Comentario with id: " + id);
    }
}
