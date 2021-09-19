package com.dio.api.biblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EditoraNotFoundException extends Exception{
   
	private static final long serialVersionUID = -7853820957731113967L;

	public EditoraNotFoundException(Long id){
        super("Editora não encontrada com o ID " +id);
    }
}
