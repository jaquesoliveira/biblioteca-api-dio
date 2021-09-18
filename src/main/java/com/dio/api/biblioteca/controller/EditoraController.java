package com.dio.api.biblioteca.controller;


import com.dio.api.biblioteca.dto.EditoraDTO;
import com.dio.api.biblioteca.exceptions.EditoraNotFoundException;
import com.dio.api.biblioteca.service.EditoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author jaques oliveira
 */

@RestController()
@RequestMapping("/api/v1/editoras")
public class EditoraController {

    @Autowired
    private EditoraService editoraService;

    @GetMapping("/")
    public ResponseEntity<List<EditoraDTO>> listAll() {
        return  editoraService.findAll();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EditoraDTO> insert(@RequestBody EditoraDTO editoraDTO) {
        return editoraService.save(editoraDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditoraDTO> update(@RequestBody EditoraDTO editoraDTO, @PathVariable Long id) throws EditoraNotFoundException {
        return editoraService.update(editoraDTO, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditoraDTO> findById(@PathVariable Long id) throws EditoraNotFoundException {
        return editoraService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<EditoraDTO> delete(@PathVariable Long id) throws EditoraNotFoundException{
        editoraService.delete(id);
        return ResponseEntity.ok().build();
    }
}
