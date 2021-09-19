package com.dio.api.biblioteca.controller;


import com.dio.api.biblioteca.dto.AutorDTO;
import com.dio.api.biblioteca.exceptions.AutorNotFoundException;
import com.dio.api.biblioteca.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author jaques oliveira
 */

@RestController()
@RequestMapping("/api/v1/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping("/")
    public ResponseEntity<List<AutorDTO>> listAll() throws AutorNotFoundException {
        List<AutorDTO> listaAutores = autorService.findAll();
        for(AutorDTO autorDTO : listaAutores){
           autorDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutorController.class).findById(autorDTO.getId())).withSelfRel()
           );
        }
        return  ResponseEntity.ok(listaAutores);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AutorDTO> insert(@RequestBody AutorDTO autorDTO) {
        return autorService.save(autorDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> update(@RequestBody AutorDTO autorDTO, @PathVariable Long id) throws AutorNotFoundException {
        return autorService.update(autorDTO, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> findById(@PathVariable Long id) throws AutorNotFoundException {
        AutorDTO autorDTO = autorService.findById(id);
        autorDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutorController.class).listAll()).withRel("listAll"));
        return ResponseEntity.ok(autorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity delete(@PathVariable Long id) throws AutorNotFoundException{
        autorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
