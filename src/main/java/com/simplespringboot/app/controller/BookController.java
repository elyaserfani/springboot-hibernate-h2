package com.simplespringboot.app.controller;

import com.simplespringboot.app.dto.request.CreateBookRequestDto;
import com.simplespringboot.app.dto.response.BookResponseDto;
import com.simplespringboot.app.global.CustomPageDto;
import com.simplespringboot.app.service.BookService;
import com.simplespringboot.app.service.UserService;
import com.simplespringboot.app.utility.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(responseCode = "200", description = "Book created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BookResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Author not found", content = {@Content(mediaType = "application/json")}),
    })
    public ResponseEntity<?> createBook(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateBookRequestDto createBookRequestDto) {
       return bookService.createBook(token, createBookRequestDto);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get all books with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book lists", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomPageDto.class))}),
    })
    public CustomPageDto<BookResponseDto> getAllBooks(@RequestParam(value = "pageNumber" , defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize" , defaultValue = "10") int pageSize) {
        return bookService.getAllBooks(pageNumber,pageSize);
    }
}
