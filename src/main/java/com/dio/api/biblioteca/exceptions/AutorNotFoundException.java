package com.dio.api.biblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AutorNotFoundException extends Exception{
  
	private static final long serialVersionUID = 6873618264027613332L;

	public AutorNotFoundException(Long id){
        super("Pessoa não encontrada com o ID " +id);
    }
}
