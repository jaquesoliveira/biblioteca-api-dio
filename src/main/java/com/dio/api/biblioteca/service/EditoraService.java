package com.dio.api.biblioteca.service;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dio.api.biblioteca.dto.EditoraDTO;
import com.dio.api.biblioteca.entity.EditoraEntity;
import com.dio.api.biblioteca.exceptions.EditoraNotFoundException;
import com.dio.api.biblioteca.repository.EditoraRepository;

@Service
public class EditoraService {

    @Autowired
    private EditoraRepository editoraRepository;

    public ResponseEntity<EditoraDTO> save(EditoraDTO editoraDTO) {
        EditoraEntity editoraEntity = toModel(editoraDTO);
        EditoraEntity savedEditoraEntity = editoraRepository.save(editoraEntity);
        EditoraDTO returnedEditora = toDTO(savedEditoraEntity);
        return ok(returnedEditora);
    }

    public List<EditoraDTO> findAll(){
        List<EditoraEntity> listAll = editoraRepository.findAll();

        List<EditoraDTO> listToReturn = listAll.stream()
                .map(item -> toDTO(item))
                .collect(Collectors.toList());

        return listToReturn;
    }

    public EditoraDTO findById(Long id) throws EditoraNotFoundException {
        EditoraEntity entityFounded = editoraRepository.findById(id)
                .orElseThrow(() -> new EditoraNotFoundException(id));
        return toDTO(entityFounded);
    }
    public ResponseEntity<EditoraDTO> update(EditoraDTO EditoraDTO, Long id) throws EditoraNotFoundException {
        EditoraEntity EditoraEntity = verifyIfExists(id);

        EditoraEntity EditoraEntityToUpdate = EditoraEntity.builder()
            .id(id)
            .nome(EditoraDTO.getNome())
            .build();

        EditoraEntity EditoraEntityUpdated = editoraRepository.save(EditoraEntityToUpdate);

        return  ResponseEntity.ok(toDTO(EditoraEntityUpdated));
    }

    public void delete(Long id) throws EditoraNotFoundException {
        verifyIfExists(id);
        editoraRepository.deleteById(id);
    }

    public EditoraEntity toModel(EditoraDTO EditoraDTO){
        return EditoraEntity.builder()
                .nome(EditoraDTO.getNome())
                .build();
    }

    public EditoraDTO toDTO(EditoraEntity EditoraEntity){
        return EditoraDTO.builder()
                .nome(EditoraEntity.getNome())
                .id(EditoraEntity.getId())
                .build();
    }

    private EditoraEntity verifyIfExists(Long id) throws EditoraNotFoundException {
        return editoraRepository.findById(id)
                .orElseThrow(() -> new EditoraNotFoundException(id));
    }
}
