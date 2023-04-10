package com.simplespringboot.app.service.book;

import com.simplespringboot.app.dto.request.CreateBookRequestDto;
import com.simplespringboot.app.dto.response.BookResponseDto;
import com.simplespringboot.app.dto.response.CreateBookResponseDto;
import com.simplespringboot.app.entity.Book;
import com.simplespringboot.app.entity.User;
import com.simplespringboot.app.mapper.AutoBookMapper;
import com.simplespringboot.app.repository.BookRepository;
import com.simplespringboot.app.repository.UserRepository;
import com.simplespringboot.app.utility.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<?> createBook(String token, CreateBookRequestDto createBookRequestDto) {
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        User author = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found"));
        Book book = new Book();
        book.setAuthor(author);
        book.setName(createBookRequestDto.getName());
        book.setDescription(createBookRequestDto.getDescription());
        book.setShaparakCode(createBookRequestDto.getShaparak_code());
        Book savedBook = bookRepository.save(book);
        CreateBookResponseDto createBookResponseDto = new CreateBookResponseDto(savedBook.getId(),savedBook.getName(),savedBook.getDescription());
        return ResponseEntity.ok(createBookResponseDto);
    }
    public List<BookResponseDto> getAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> list = bookRepository.findAll(pageable);
        return list.stream().map((book) -> AutoBookMapper.INSTANCE.mapToBookResponseDto(book)).collect(Collectors.toList());
    }
}
