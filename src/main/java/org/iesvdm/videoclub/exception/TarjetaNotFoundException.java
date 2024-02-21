package org.iesvdm.videoclub.exception;

public class TarjetaNotFoundException extends RuntimeException{
    public TarjetaNotFoundException(Long id){super("Not found Tarjeta con id " + id);}
}
