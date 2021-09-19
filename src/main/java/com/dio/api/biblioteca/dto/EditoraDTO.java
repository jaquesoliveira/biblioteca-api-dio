package com.dio.api.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditoraDTO extends RepresentationModel<EditoraDTO> {

    public Long id;

    public String nome;
}
