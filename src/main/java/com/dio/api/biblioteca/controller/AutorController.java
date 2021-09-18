package com.dio.api.biblioteca.controller;


import com.dio.api.biblioteca.entity.AutorEntity;
import com.dio.api.biblioteca.exceptions.AutorNotFoundException;
import com.dio.api.biblioteca.service.AutorService;
import com.dio.api.biblioteca.dto.AutorDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<AutorDTO>> listAll() {
        return  autorService.findAll();
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
        return autorService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity delete(@PathVariable Long id) throws AutorNotFoundException{
        autorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
