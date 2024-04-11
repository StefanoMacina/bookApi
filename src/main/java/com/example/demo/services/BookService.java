package com.example.demo.services;

import com.example.demo.domain.entities.BookEntity;
import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity save(BookEntity bookEntity);

    BookEntity modifyBookByIsbn(String isbn, BookEntity bookEntity);

    List<BookEntity> fetchAllBooks();

    boolean existByid(String id);

    void deleteById(String id);

    Optional<BookEntity> findById(String isbn);
}
