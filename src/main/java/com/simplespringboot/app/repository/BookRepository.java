package com.simplespringboot.app.repository;

import com.simplespringboot.app.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book save(Book book);
    List<Book> findByName(String name);
    List<Book> findByDescription(String description);

    Page<Book> findAll(Pageable pageable);

}