package com.marcuslull.bookmanager.entities;

import com.marcuslull.bookmanager.dtos.BookDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "books")
public class BookEntity {

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

    public BookEntity() {};

    public BookEntity(String title, Integer pages, Integer bookNumber, Boolean finished) {
        this.title = title;
        this.pages = pages;
        this.bookNumber = bookNumber;
        this.finished = finished;
    }

    public BookEntity(Long id, String title, Integer pages, Integer bookNumber, Boolean finished) {
        this.id = id;
        this.title = title;
        this.pages = pages;
        this.bookNumber = bookNumber;
        this.finished = finished;
    }

    public static BookEntity fromDTO(BookDto bookDto) {
        return new BookEntity(bookDto.title(), bookDto.pages(), bookDto.bookNumber(), bookDto.finished());
    }

    public static BookDto toDto(BookEntity bookEntity) {
        return new BookDto(bookEntity.getTitle(), bookEntity.getPages(), bookEntity.getBookNumber(), bookEntity.getFinished());
    }
}
