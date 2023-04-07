package com.simplespringboot.app.Repository;

import com.simplespringboot.app.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByName(String name);
    List<Book> findByDescription(String description);
}