package com.dio.api.biblioteca.controller;


import com.dio.api.biblioteca.dto.EditoraDTO;
import com.dio.api.biblioteca.exceptions.EditoraNotFoundException;
import com.dio.api.biblioteca.service.EditoraService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author jaques oliveira
 */

@RestController()
@RequestMapping("/api/v1/editoras")
public class EditoraController {

    @Autowired
    private EditoraService editoraService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de auotores"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/")
    public ResponseEntity<List<EditoraDTO>> listAll() throws EditoraNotFoundException {
        List<EditoraDTO> listaEditoras = editoraService.findAll();

        for(EditoraDTO editoraDTO : listaEditoras){
            editoraDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EditoraController.class).findById(editoraDTO.getId())).withSelfRel());
        }
        return  ResponseEntity.ok(listaEditoras);
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
        EditoraDTO editoraDTO = editoraService.findById(id);
        editoraDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EditoraController.class).listAll()).withRel("listAll"));
        return ResponseEntity.ok(editoraDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<EditoraDTO> delete(@PathVariable Long id) throws EditoraNotFoundException{
        editoraService.delete(id);
        return ResponseEntity.ok().build();
    }
}
