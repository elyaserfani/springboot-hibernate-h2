package com.simplespringboot.app.mapper;

import com.simplespringboot.app.dto.response.BookResponseDto;
import com.simplespringboot.app.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AutoBookMapper {
    AutoBookMapper INSTANCE = Mappers.getMapper(AutoBookMapper.class);
    BookResponseDto mapToBookResponseDto(Book book);
    Book mapToBook(BookResponseDto bookResponseDto);
}