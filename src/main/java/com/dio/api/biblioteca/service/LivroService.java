package com.dio.api.biblioteca.service;

import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dio.api.biblioteca.controller.AutorController;
import com.dio.api.biblioteca.controller.EditoraController;
import com.dio.api.biblioteca.controller.LivroController;
import com.dio.api.biblioteca.dto.AutorDTO;
import com.dio.api.biblioteca.dto.EditoraDTO;
import com.dio.api.biblioteca.entity.AutorEntity;
import com.dio.api.biblioteca.entity.EditoraEntity;
import com.dio.api.biblioteca.exceptions.AutorNotFoundException;
import com.dio.api.biblioteca.exceptions.EditoraNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dio.api.biblioteca.dto.LivroDTO;
import com.dio.api.biblioteca.entity.LivroEntity;
import com.dio.api.biblioteca.exceptions.LivroNotFoundException;
import com.dio.api.biblioteca.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditoraService editoraService;

    public ResponseEntity<LivroDTO> save(LivroDTO livroDTO) throws EditoraNotFoundException {
        LivroEntity livroEntity = toModel(livroDTO);
        LivroEntity savedLivroEntity = livroRepository.save(livroEntity);
        LivroDTO returnedLivro = toDTO(savedLivroEntity);
        return ok(returnedLivro);
    }

    public List<LivroDTO> findAll() throws EditoraNotFoundException, LivroNotFoundException, AutorNotFoundException {
        List<LivroEntity> listAll = livroRepository.findAll();

        List<LivroDTO> listToReturn = listAll.stream()
                .map(item -> toDTO(item))
                .collect(Collectors.toList());

        addLinks(listToReturn);

        return listToReturn;
    }

    public List<LivroDTO> addLinks(List<LivroDTO> listLivrosDTO) throws EditoraNotFoundException, LivroNotFoundException, AutorNotFoundException {

        for(LivroDTO livroDTO : listLivrosDTO){
            Long editoraId = livroDTO.getEditora().getId();

            livroDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LivroController.class).findById(livroDTO.getId())).withSelfRel());

            if(livroDTO.getEditora().getLinks() == null || livroDTO.getEditora().getLinks().isEmpty()){
                livroDTO.getEditora().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EditoraController.class).findById(editoraId)).withSelfRel());
            }

            for(AutorEntity autorEntity : livroDTO.getAutores()){
                if(autorEntity.getLinks() == null || autorEntity.getLinks().isEmpty()) {
                    autorEntity.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutorController.class).findById(autorEntity.getId())).withSelfRel());
                }
            }
        }

        return listLivrosDTO;
    }

    public LivroDTO findById(Long id) throws LivroNotFoundException {
        LivroEntity entityFounded = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNotFoundException(id));
        return toDTO(entityFounded);
    }
    public ResponseEntity<LivroDTO> update(LivroDTO livroDTO, Long id) throws LivroNotFoundException {
        LivroEntity livroEntity = verifyIfExists(id);

        LivroEntity livroEntityToUpdate = livroEntity.builder()
            .id(id)
            .nome(livroDTO.getNome())
            .listaAutorEntity( livroDTO.getAutores())
            .editoraEntity(livroDTO.getEditora())
            .build();

        LivroEntity LivroEntityUpdated = livroRepository.save(livroEntityToUpdate);

        return  ResponseEntity.ok(toDTO(LivroEntityUpdated));
    }

    public void delete(Long id) throws LivroNotFoundException {
        verifyIfExists(id);
        livroRepository.deleteById(id);
    }

    private LivroEntity toModel(LivroDTO livroDTO){
        return LivroEntity.builder()
                .nome(livroDTO.getNome())
                .listaAutorEntity(livroDTO.getAutores())
                .editoraEntity(livroDTO.getEditora())
                .build();
    }

    private LivroDTO toDTO(LivroEntity livroEntity){
        return LivroDTO.builder()
                .nome(livroEntity.getNome())
                .id(livroEntity.getId())
                .autores(livroEntity.getListaAutorEntity())
                .editora(livroEntity.getEditoraEntity())
                .build();
    }

    private List<AutorDTO> toDTOListAutor(List<AutorEntity> listAutorEntity){

        List<AutorDTO> listAuotorDTO = new ArrayList<>();

        for(AutorEntity autorEntity : listAutorEntity){
            listAuotorDTO.add(autorService.toDTO(autorEntity));
        }

        return listAuotorDTO;
    }

    private List<AutorEntity> toEntityListAutor(List<AutorDTO> listAutorDTO){

        List<AutorEntity> listAuotorEntity = new ArrayList<>();

        for(AutorDTO autorDto : listAutorDTO){
            listAuotorEntity.add(autorService.toModel(autorDto));
        }

        return listAuotorEntity;
    }

    private LivroEntity verifyIfExists(Long id) throws LivroNotFoundException {
        return livroRepository.findById(id)
                .orElseThrow(() -> new LivroNotFoundException(id));
    }
}
