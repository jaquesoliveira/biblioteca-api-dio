package com.dio.api.biblioteca.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 100)
    public String nome;
    
    @OneToOne(targetEntity = EditoraEntity.class)
    @JoinColumn(name="idEditora", referencedColumnName = "id")
    public EditoraEntity editoraEntity;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name="LIVRO_AUTOR", joinColumns = @JoinColumn(name="idLivro"),
    inverseJoinColumns = @JoinColumn(name="idAutor"))
    public List<AutorEntity> listaAutorEntity;
}
