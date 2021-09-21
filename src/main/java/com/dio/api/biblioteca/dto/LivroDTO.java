package com.dio.api.biblioteca.dto;

import com.dio.api.biblioteca.entity.AutorEntity;
import com.dio.api.biblioteca.entity.EditoraEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO  extends RepresentationModel<LivroDTO> {

    public Long id;

    public String nome;
        
    public EditoraEntity editora;
        
    public List<AutorEntity> autores;
}
