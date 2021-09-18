package com.dio.api.biblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AutorNotFoundException extends Exception{
    public AutorNotFoundException(Long id){
        super("Pessoa não encontrada com o ID " +id);
    }
}
