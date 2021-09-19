package com.dio.api.biblioteca.service;

import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dio.api.biblioteca.dto.AutorDTO;
import com.dio.api.biblioteca.dto.EditoraDTO;
import com.dio.api.biblioteca.entity.AutorEntity;
import com.dio.api.biblioteca.entity.EditoraEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ResponseEntity<LivroDTO> save(LivroDTO livroDTO) {
        LivroEntity editoraEntity = toModel(livroDTO);
        LivroEntity savedLivroEntity = livroRepository.save(editoraEntity);
        LivroDTO returnedLivro = toDTO(savedLivroEntity);
        return ok(returnedLivro);
    }

    public List<LivroDTO> findAll(){
        List<LivroEntity> listAll = livroRepository.findAll();

        List<LivroDTO> listToReturn = listAll.stream()
                .map(item -> toDTO(item))
                .collect(Collectors.toList());

        return listToReturn;
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
            .listaAutorEntity( toEntityListAutor( livroDTO.getAutores()))
            .editoraEntity(editoraService.toModel(livroDTO.getEditora()))
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
                .listaAutorEntity(toEntityListAutor( livroDTO.getAutores()))
                .editoraEntity(editoraService.toModel(livroDTO.getEditora()))
                .build();
    }

    private LivroDTO toDTO(LivroEntity livroEntity){
        return LivroDTO.builder()
                .nome(livroEntity.getNome())
                .id(livroEntity.getId())
                .autores(toDTOListAutor(livroEntity.getListaAutorEntity()))
                .editora(editoraService.toDTO(livroEntity.getEditoraEntity()))
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
