package com.example.demo.services.Impl;

import com.example.demo.domain.DTO.AuthorDto;
import com.example.demo.domain.entities.AuthorEntity;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.services.AuthorService;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public AuthorEntity modifyAuthorById(Long id, AuthorEntity authorEntity) {
        authorEntity.setAuthors_id(id);
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> fetchAllAuthors() {
        return (List<AuthorEntity>) authorRepository.findAll();
    }

    @Override
    public boolean existById(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public Optional<AuthorEntity> getOneAuthor(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }


}
