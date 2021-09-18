package com.dio.api.biblioteca.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dio.api.biblioteca.dto.LivroDTO;
import com.dio.api.biblioteca.exceptions.LivroNotFoundException;
import com.dio.api.biblioteca.service.LivroService;


/**
 * @author jaques oliveira
 */

@RestController()
@RequestMapping("/api/v1/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping("/")
    public ResponseEntity<List<LivroDTO>> listAll() {
        return  livroService.findAll();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LivroDTO> insert(@RequestBody LivroDTO livroDTO) {
        return livroService.save(livroDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> update(@RequestBody LivroDTO livroDTO, @PathVariable Long id) throws LivroNotFoundException {
        return livroService.update(livroDTO, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> findById(@PathVariable Long id) throws LivroNotFoundException {
        return livroService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<LivroDTO> delete(@PathVariable Long id) throws LivroNotFoundException{
        livroService.delete(id);
        return ResponseEntity.ok().build();
    }
}
