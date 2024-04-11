package com.example.demo.services;

import com.example.demo.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;


public interface AuthorService {

    AuthorEntity save(AuthorEntity authorEntity);

    AuthorEntity modifyAuthorById (Long id, AuthorEntity authorEntity);

    List<AuthorEntity> fetchAllAuthors();

    boolean existById(Long id);

    Optional<AuthorEntity> findById(Long id);

    void deleteById(Long id);
}
