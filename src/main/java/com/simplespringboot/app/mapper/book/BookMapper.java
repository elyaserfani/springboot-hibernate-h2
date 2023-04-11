package com.simplespringboot.app.mapper.book;

import com.simplespringboot.app.dto.response.BookResponseDto;
import com.simplespringboot.app.entity.Book;
import com.simplespringboot.app.global.CustomMeta;
import com.simplespringboot.app.global.CustomPageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    BookResponseDto mapEntityToBookResponse(Book book);
    Book mapBookResponseToEntity(BookResponseDto bookResponseDto);

    @Mapping(source = "",target = "")
    List<BookResponseDto> mapListOfEntityToListOfBookResponse(List<Book> entities);
    default CustomPageDto<BookResponseDto> mapDefaultPaginationToCustomPagination(Page<Book> page) {
        CustomMeta meta = new CustomMeta(page.getNumber(), page.getSize(), page.getTotalElements());
        List<BookResponseDto> items = mapListOfEntityToListOfBookResponse(page.getContent());
        return new CustomPageDto<>(items, meta);
    }
}