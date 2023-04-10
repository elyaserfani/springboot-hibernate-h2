package com.simplespringboot.app.controller;

import com.simplespringboot.app.dto.request.CreateBookRequest;
import com.simplespringboot.app.entity.Book;
import com.simplespringboot.app.entity.User;
import com.simplespringboot.app.repository.BookRepository;
import com.simplespringboot.app.repository.UserRepository;
import com.simplespringboot.app.service.book.BookService;
import com.simplespringboot.app.service.user.UserService;
import com.simplespringboot.app.utility.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/book")
@Tag(name = "Book",description = "Book Controller")

public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Create book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "404", description = "Author not found", content = {@Content(mediaType = "application/json")}),
    })
    public ResponseEntity<?> createBook(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateBookRequest createBookRequest) {
       return bookService.createBook(token,createBookRequest);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get all books with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book lists", content = {@Content(mediaType = "application/json")}),
    })
    public Page<Book> getAllBooks(@RequestParam(value = "pageNumber" , defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize" , defaultValue = "10") int pageSize) {
        return bookService.getAllBooks(pageNumber,pageSize);
    }
}
