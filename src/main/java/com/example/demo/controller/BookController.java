package com.example.demo.controller;

import com.example.demo.domain.DTO.BookDto;
import com.example.demo.domain.entities.BookEntity;
import com.example.demo.mappers.Mapper;
import com.example.demo.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;
    private final Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping(path = "/books")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto){
        if(bookService.existByid(bookDto.getIsbn())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.createBook(bookEntity);

        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getOneBook(@PathVariable("isbn") String isbn){
        Optional<BookEntity> foundBook = bookService.getOneBook(isbn);

        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> modifyBookByIsbn(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBookEntity = bookService.modifyBookByIsbn(isbn, bookEntity);

        return new ResponseEntity<>( bookMapper.mapTo(updatedBookEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<BookDto>> fetchAllBooks(){
        List<BookDto> dtoList = new ArrayList<>();
        for(var bookEntity : bookService.fetchAllBooks()){
            dtoList.add(bookMapper.mapTo(bookEntity));
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> deleteOneBook(
            @PathVariable("isbn") String isbn
    ){
        if(!bookService.existByid(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteById(isbn);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
