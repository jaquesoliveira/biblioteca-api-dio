package com.dio.api.biblioteca.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AutorDTO {

    public Long id;

    public String nome;
}
