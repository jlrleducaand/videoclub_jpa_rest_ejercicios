package org.iesvdm.videoclub.exception;

public class CategoriaDTONotFoundException extends RuntimeException {
    public CategoriaDTONotFoundException(Long id) {
        super("Not found Categoria with id: " + id);
    }
}