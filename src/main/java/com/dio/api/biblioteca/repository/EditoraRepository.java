package com.dio.api.biblioteca.repository;

import com.dio.api.biblioteca.entity.EditoraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditoraRepository extends JpaRepository<EditoraEntity, Long> {

}
