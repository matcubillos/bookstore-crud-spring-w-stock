package com.example.bookstorecrud.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String author;
    private String title;
    private String description;
    private Integer pagesNumber;
    private Integer price;

    private Integer stock;
    @Temporal(TemporalType.DATE)
    private LocalDate publicationDate;

    @PrePersist
    public void prePersist() {
        this.publicationDate = LocalDate.now();
    }
}
