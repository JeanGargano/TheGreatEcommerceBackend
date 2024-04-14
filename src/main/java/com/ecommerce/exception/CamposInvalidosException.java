package com.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CamposInvalidosException extends RuntimeException{
    public CamposInvalidosException(String mensaje){
        super(mensaje);
    }
}
