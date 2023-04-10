package com.simplespringboot.app.service.book;

import com.simplespringboot.app.entity.Book;
import com.simplespringboot.app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    Book saveBook(Book book);
    List<Book> findByName(String name);
    List<Book> findByDescription(String description);

    Page<Book> findAll(Pageable pageable);
}
