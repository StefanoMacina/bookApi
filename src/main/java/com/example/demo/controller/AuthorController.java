package com.example.demo.controller;

import com.example.demo.controller.patcher.Patcher;
import com.example.demo.domain.DTO.AuthorDto;
import com.example.demo.domain.entities.AuthorEntity;
import com.example.demo.mappers.Mapper;
import com.example.demo.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class AuthorController{

    @Autowired
    AuthorService authorService;

    @Autowired
    Mapper<AuthorEntity, AuthorDto> authorMapper;


    @PostMapping("/authors")
    public AuthorDto createAuthor(@RequestBody AuthorDto authorDto){
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return authorMapper.mapTo(savedAuthorEntity);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto
    ){
       AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
       AuthorEntity updatedAuthorEntity = authorService.modifyAuthorById(id,authorEntity);

       return new ResponseEntity<>(authorMapper.mapTo(authorEntity), HttpStatus.OK);
    }

    @GetMapping("/authors")
    public List<AuthorDto> authorsList(){
        List<AuthorDto> dtoList = new ArrayList<>();
        for(var authorEntity : authorService.fetchAllAuthors()){
            dtoList.add(authorMapper.mapTo(authorEntity));
        }
        return dtoList;
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> getOneAuthor(
            @PathVariable("id") Long id
    ){
           Optional<AuthorEntity> foundAuthor = authorService.findById(id);

           return foundAuthor.map(authorEntity -> {
               AuthorDto authorDto = authorMapper.mapTo(authorEntity);
               return new ResponseEntity<>(authorDto, HttpStatus.OK);
           }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> deleteAuthor(
        @PathVariable("id") Long id
    ){
        if(!authorService.existById(id)){
            return ResponseEntity.notFound().build();
        }
        authorService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> patchAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto incompleteAuthorDto
    ){
        Patcher<AuthorDto> authorDtoPatcher = new Patcher<>(AuthorDto.class);
        Optional<AuthorEntity> existingAuthor = authorService.findById(id);

        return existingAuthor.map(existingAuthorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(existingAuthorEntity);
            try {
                authorDtoPatcher.patcher(authorDto, incompleteAuthorDto);
                AuthorEntity updatedAuthorEntity = authorMapper.mapFrom(authorDto);
                authorService.save(updatedAuthorEntity);
                return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity), HttpStatus.OK);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
