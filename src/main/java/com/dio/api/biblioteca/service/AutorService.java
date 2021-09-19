package com.dio.api.biblioteca.service;

import com.dio.api.biblioteca.dto.AutorDTO;
import com.dio.api.biblioteca.entity.AutorEntity;
import com.dio.api.biblioteca.exceptions.AutorNotFoundException;
import com.dio.api.biblioteca.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public ResponseEntity<AutorDTO> save(AutorDTO autorDTO) {
        AutorEntity autorEntity = toModel(autorDTO);
        AutorEntity savedAutorEntity = autorRepository.save(autorEntity);
        AutorDTO returnedAutor = toDTO(savedAutorEntity);
        return ok(returnedAutor);
    }

    public ResponseEntity<List<AutorDTO>> findAll(){
        List<AutorEntity> listAll = autorRepository.findAll();

        List<AutorDTO> listToReturn = listAll.stream()
                .map(item -> toDTO(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listToReturn);
    }

//    public ResponseEntity<AutorDTO> findById(Long id) throws AutorNotFoundException {
//        AutorEntity entityFounded = autorRepository.findById(id)
//                .orElseThrow(() -> new AutorNotFoundException(id));
//        return ResponseEntity.ok(toDTO(entityFounded));
//    }

    public AutorDTO findById(Long id) throws AutorNotFoundException {
        AutorEntity entityFounded = autorRepository.findById(id)
                .orElseThrow(() -> new AutorNotFoundException(id));
        return toDTO(entityFounded);
    }

    public ResponseEntity<AutorDTO> update(AutorDTO autorDTO, Long id) throws AutorNotFoundException {
        AutorEntity autorEntity = verifyIfExists(id);

        AutorEntity autorEntityToUpdate = autorEntity.builder()
            .id(id)
            .nome(autorDTO.getNome())
            .build();

        AutorEntity autorEntityUpdated = autorRepository.save(autorEntityToUpdate);

        return  ResponseEntity.ok(toDTO(autorEntityUpdated));
    }

    public void delete(Long id) throws AutorNotFoundException {
        verifyIfExists(id);
        autorRepository.deleteById(id);
    }

    private AutorEntity toModel(AutorDTO autorDTO){
        return AutorEntity.builder()
                .nome(autorDTO.getNome())
                .build();
    }

    private AutorDTO toDTO(AutorEntity autorEntity){
        return AutorDTO.builder()
                .nome(autorEntity.getNome())
                .id(autorEntity.getId())
                .build();
    }

    private AutorEntity verifyIfExists(Long id) throws AutorNotFoundException {
        return autorRepository.findById(id)
                .orElseThrow(() -> new AutorNotFoundException(id));
    }
}
