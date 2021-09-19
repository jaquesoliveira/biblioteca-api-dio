package com.dio.api.biblioteca.controller;


import java.util.List;

import com.dio.api.biblioteca.dto.AutorDTO;
import com.dio.api.biblioteca.dto.EditoraDTO;
import com.dio.api.biblioteca.entity.AutorEntity;
import com.dio.api.biblioteca.entity.EditoraEntity;
import com.dio.api.biblioteca.exceptions.AutorNotFoundException;
import com.dio.api.biblioteca.exceptions.EditoraNotFoundException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de auotores"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/")
    public ResponseEntity<List<LivroDTO>> listAll() throws LivroNotFoundException, EditoraNotFoundException, AutorNotFoundException {
        List<LivroDTO> listToReturn = livroService.findAll();
        return ResponseEntity.ok(listToReturn);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LivroDTO> insert(@RequestBody LivroDTO livroDTO) throws EditoraNotFoundException {

        return livroService.save(livroDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> update(@RequestBody LivroDTO livroDTO, @PathVariable Long id) throws LivroNotFoundException {
        return livroService.update(livroDTO, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> findById(@PathVariable Long id) throws LivroNotFoundException, EditoraNotFoundException, AutorNotFoundException {
        LivroDTO livroDTO = livroService.findById(id);
        livroDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LivroController.class).listAll()).withRel("listAll"));
        livroDTO.getEditora().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EditoraController.class).findById(livroDTO.getEditora().getId())).withSelfRel());

        for(AutorEntity autorEntity : livroDTO.getAutores()){
            autorEntity.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutorController.class).findById(autorEntity.getId())).withSelfRel());
        }
        return ResponseEntity.ok(livroDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<LivroDTO> delete(@PathVariable Long id) throws LivroNotFoundException{
        livroService.delete(id);
        return ResponseEntity.ok().build();
    }
}
