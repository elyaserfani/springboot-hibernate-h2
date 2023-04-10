package com.simplespringboot.app.service.book;

import com.simplespringboot.app.dto.request.CreateBookRequest;
import com.simplespringboot.app.dto.request.LoginRequest;
import com.simplespringboot.app.dto.response.JwtResponse;
import com.simplespringboot.app.entity.Book;
import com.simplespringboot.app.entity.User;
import com.simplespringboot.app.repository.BookRepository;
import com.simplespringboot.app.repository.UserRepository;
import com.simplespringboot.app.service.user.UserService;
import com.simplespringboot.app.service.user.detail.UserDetailsImpl;
import com.simplespringboot.app.utility.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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

    public ResponseEntity<?> createBook(String token, CreateBookRequest createBookRequest) {
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(7));
        User author = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found"));
        Book book = new Book();
        book.setAuthor(author);
        book.setName(createBookRequest.getName());
        book.setDescription(createBookRequest.getDescription());
        return ResponseEntity.ok(bookRepository.save(book));
    }
    public Page<Book> getAllBooks(int pageNumber,int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAll(pageable);
    }
}
