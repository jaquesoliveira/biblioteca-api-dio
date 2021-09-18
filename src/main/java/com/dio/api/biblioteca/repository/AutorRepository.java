package com.dio.api.biblioteca.repository;

import com.dio.api.biblioteca.entity.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<AutorEntity, Long> {

}
