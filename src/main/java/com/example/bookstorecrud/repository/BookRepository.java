package com.example.bookstorecrud.repository;

import com.example.bookstorecrud.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
