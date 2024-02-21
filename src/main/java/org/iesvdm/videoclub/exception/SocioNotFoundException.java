package org.iesvdm.videoclub.exception;

public class SocioNotFoundException extends RuntimeException {
    public SocioNotFoundException(String id) {
        super("Not found Socio with id: " + id);
    }
}

