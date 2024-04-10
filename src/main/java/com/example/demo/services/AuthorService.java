package com.example.demo.services;

import com.example.demo.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;


public interface AuthorService {

    AuthorEntity createAuthor(AuthorEntity authorEntity);

    AuthorEntity modifyAuthorById (Long id, AuthorEntity authorEntity);

    List<AuthorEntity> fetchAllAuthors();

    boolean existById(Long id);

    Optional<AuthorEntity> getOneAuthor(Long id);

    void deleteById(Long id);
}
