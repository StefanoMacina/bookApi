package com.example.demo.controller;

import com.example.demo.controller.patcher.Patcher;
import com.example.demo.domain.DTO.AuthorDto;
import com.example.demo.domain.DTO.BookDto;
import com.example.demo.domain.entities.BookEntity;
import com.example.demo.mappers.Mapper;
import com.example.demo.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
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
        BookEntity savedBookEntity = bookService.save(bookEntity);

        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getOneBook(@PathVariable("isbn") String isbn){
        Optional<BookEntity> foundBook = bookService.findById(isbn);

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

    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDto> patchBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto incompleteBook
    ){
        Patcher<BookDto> bookDtoPatcher = new Patcher<>(BookDto.class);
        Optional<BookEntity> existingBook = bookService.findById(isbn);

        return existingBook.map( existingBookEntity -> {
            BookDto bookDto = bookMapper.mapTo(existingBookEntity);
            try {
                bookDtoPatcher.patcher(bookDto,incompleteBook);
                BookEntity updatedBookEntity = bookMapper.mapFrom(bookDto);
                bookService.save(updatedBookEntity);
                return new ResponseEntity<>(bookMapper.mapTo(updatedBookEntity),HttpStatus.OK);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
