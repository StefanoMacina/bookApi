package com.example.demo.mappers.impl;

import com.example.demo.config.MapperConfig;
import com.example.demo.domain.DTO.AuthorDto;
import com.example.demo.domain.entities.AuthorEntity;
import com.example.demo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
