package com.dio.api.biblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LivroNotFoundException extends Exception{
   
	private static final long serialVersionUID = -728456085183914246L;

	public LivroNotFoundException(Long id){
        super("Livro não encontrada com o ID " +id);
    }
}
