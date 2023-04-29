package com.simplespringboot.app.service;

import com.simplespringboot.app.dto.request.CreateBookRequestDto;
import com.simplespringboot.app.dto.response.BookResponseDto;
import com.simplespringboot.app.entity.Book;
import com.simplespringboot.app.entity.User;
import com.simplespringboot.app.exception.ErrorResponse;
import com.simplespringboot.app.global.CustomPageDto;
import com.simplespringboot.app.mapper.BookMapper;
import com.simplespringboot.app.repository.BookRepository;
import com.simplespringboot.app.repository.UserRepository;
import com.simplespringboot.app.utility.JwtUtils;
import com.simplespringboot.app.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<?> createBook(CreateBookRequestDto createBookRequestDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User author = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found"));
        Book book = new Book();
        book.setAuthor(author);
        book.setName(createBookRequestDto.getName());
        book.setDescription(createBookRequestDto.getDescription());
        book.setShaparakCode(createBookRequestDto.getShaparak_code());
        Book savedBook = bookRepository.save(book);
        BookResponseDto createBookResponseDto = new BookResponseDto(savedBook.getId(),savedBook.getName(),savedBook.getDescription());
        return ResponseEntity.ok(createBookResponseDto);
    }
    public CustomPageDto<BookResponseDto> getAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> paginatedList = bookRepository.findAll(pageable);
        return BookMapper.INSTANCE.mapDefaultPaginationToCustomPagination(paginatedList);
    }

    public ResponseEntity<?> updateBook(int id, CreateBookRequestDto book) {
        Optional<Book> existingBook = bookRepository.findById(Long.valueOf(id));
        if (!existingBook.isPresent()){
            return Utility.buildResponseEntity(new ErrorResponse(HttpStatus.NOT_FOUND,"Book not found"));
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User author = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found"));
        existingBook.get().setDescription(book.getDescription());
        existingBook.get().setAuthor(book.getAuthor());
        existingBook.get().setName(book.getName());
        existingBook.get().setShaparakCode(book.getShaparak_code());
        existingBook.get().setAuthor(author);
        bookRepository.save(existingBook.get());
        return Utility.buildResponseEntity(new ErrorResponse(HttpStatus.OK,"Book updated"));
    }
}
