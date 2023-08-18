package com.example.bookstorecrud.services;

import com.example.bookstorecrud.models.Book;
import com.example.bookstorecrud.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional()
    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    @Transactional()
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional()
    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional()
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional()
    public void reduceStock(Long id, int quantity) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
             Book book = optionalBook.get();
            int currentStock = book.getStock();
            if (currentStock >= quantity) {
                book.setStock(currentStock - quantity);
                bookRepository.save(book);
            } else {
                throw new IllegalArgumentException("Not enough stock available");
            }
        } else {
            throw new NoSuchElementException("Product not found");
        }
    }
}
