package com.example.demo.mappers.impl;

import com.example.demo.config.MapperConfig;
import com.example.demo.domain.DTO.BookDto;
import com.example.demo.domain.entities.BookEntity;
import com.example.demo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
