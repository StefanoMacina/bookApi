package com.example.demo.services.Impl;

import com.example.demo.domain.entities.BookEntity;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    @Override
    public BookEntity modifyBookByIsbn(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> fetchAllBooks() {
        return (List<BookEntity>) bookRepository.findAll();
    }

    @Override
    public boolean existByid(String id) {
        return bookRepository.existsById(id);
    }

    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<BookEntity> getOneBook(String isbn) {
        return bookRepository.findById(isbn);
    }


}
