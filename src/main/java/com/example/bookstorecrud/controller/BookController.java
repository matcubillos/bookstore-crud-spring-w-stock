package com.example.bookstorecrud.controller;

import com.example.bookstorecrud.models.Book;
import com.example.bookstorecrud.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book books;
        books = bookService.findBookById(id);
        return new ResponseEntity<Book>(books, HttpStatus.OK);
    }

    @PostMapping("/book")
        public ResponseEntity<Book> postNewBook(@Validated @RequestBody Book books) {
        Book newBooks;
        newBooks = bookService.saveBook(books);

        return new ResponseEntity<Book>(HttpStatus.CREATED);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Book> updateBook(@Validated @RequestBody Book book, @PathVariable Long id){
        Book updatedBook;
        updatedBook = bookService.findBookById(id);

        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setPrice(book.getPrice());
        updatedBook.setDescription(book.getDescription());
        updatedBook.setPagesNumber(book.getPagesNumber());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/book/{id}/reduce-stock")
    public ResponseEntity<?> reduceStock(@PathVariable Long id, int quantity) {
        Map<String, Object> res = new HashMap<>();
        try {
            bookService.reduceStock(id, quantity);
        } catch (DataAccessException e) {
            res.put("Message", "Internal server error generating the request");
            res.put("Error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        res.put("Message", "Stock updated");
        return new ResponseEntity<Map<String, Object>>(res, HttpStatus.CREATED);
    }
}
