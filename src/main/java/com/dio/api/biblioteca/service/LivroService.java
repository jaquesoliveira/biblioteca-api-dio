package com.dio.api.biblioteca.service;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import java.util.stream.Collectors;

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

    public ResponseEntity<LivroDTO> save(LivroDTO livroDTO) {
        LivroEntity editoraEntity = toModel(livroDTO);
        LivroEntity savedLivroEntity = livroRepository.save(editoraEntity);
        LivroDTO returnedLivro = toDTO(savedLivroEntity);
        return ok(returnedLivro);
    }

    public ResponseEntity<List<LivroDTO>> findAll(){
        List<LivroEntity> listAll = livroRepository.findAll();

        List<LivroDTO> listToReturn = listAll.stream()
                .map(item -> toDTO(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listToReturn);
    }

    public ResponseEntity<LivroDTO> findById(Long id) throws LivroNotFoundException {
        LivroEntity entityFounded = livroRepository.findById(id)
                .orElseThrow(() -> new LivroNotFoundException(id));
        return ResponseEntity.ok(toDTO(entityFounded));
    }
    public ResponseEntity<LivroDTO> update(LivroDTO livroDTO, Long id) throws LivroNotFoundException {
        LivroEntity livroEntity = verifyIfExists(id);

        LivroEntity livroEntityToUpdate = livroEntity.builder()
            .id(id)
            .nome(livroDTO.getNome())
            .listaAutorEntity(livroDTO.getListaAutorEntity())
            .editoraEntity(livroDTO.getEditoraEntity())
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
                .listaAutorEntity(livroDTO.getListaAutorEntity())
                .editoraEntity(livroDTO.getEditoraEntity())
                .build();
    }

    private LivroDTO toDTO(LivroEntity livroEntity){
        return LivroDTO.builder()
                .nome(livroEntity.getNome())
                .id(livroEntity.getId())
                .listaAutorEntity(livroEntity.getListaAutorEntity())
                .editoraEntity(livroEntity.getEditoraEntity())
                .build();
    }

    private LivroEntity verifyIfExists(Long id) throws LivroNotFoundException {
        return livroRepository.findById(id)
                .orElseThrow(() -> new LivroNotFoundException(id));
    }
}
