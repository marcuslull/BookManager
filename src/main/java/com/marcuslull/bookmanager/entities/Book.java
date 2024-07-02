package com.marcuslull.bookmanager.entities;

import com.marcuslull.bookmanager.dtos.BookDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private Integer pages;

    @Column
    private Integer bookNumber;

    @Column
    private Boolean finished;

    public Book() {};

    public Book(String title, Integer pages, Integer bookNumber, Boolean finished) {
        this.title = title;
        this.pages = pages;
        this.bookNumber = bookNumber;
        this.finished = finished;
    }

    public Book(Long id, String title, Integer pages, Integer bookNumber, Boolean finished) {
        this.id = id;
        this.title = title;
        this.pages = pages;
        this.bookNumber = bookNumber;
        this.finished = finished;
    }

    public static Book fromDTO(BookDto bookDto) {
        return new Book(bookDto.title(), bookDto.pages(), bookDto.bookNumber(), bookDto.finished());
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book.getTitle(), book.getPages(), book.getBookNumber(),book.getFinished());
    }
}
