package com.marcuslull.bookmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcuslull.bookmanager.dtos.BookDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column @NotNull @Size(min = 2, max = 50)
    private String title;

    @Column @NotNull @Size(min = 3, max = 50)
    private String author;

    @Column @NotNull @Digits(integer = 4, fraction = 0) @Positive
    private Integer pages;

    @Column @NotNull @Digits(integer = 2, fraction = 0) @Positive
    private Integer bookNumber;

    @Column
    private Boolean finished;

    @JsonIgnore
    @Column
    private String dedupeId;

    public BookEntity(String title, String author, Integer pages, Integer bookNumber, Boolean finished) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.bookNumber = bookNumber;
        this.finished = finished;

        dedupeId = this.title + this.pages.toString();
    }

    public static BookEntity fromDto(@Valid BookDto bookDto) {
        return new BookEntity(bookDto.title(), bookDto.author(), bookDto.pages(), bookDto.bookNumber(), bookDto.finished());
    }

    public static BookDto toDto(@Valid BookEntity bookEntity) {
        return new BookDto(bookEntity.getTitle(), bookEntity.getAuthor(), bookEntity.getPages(), bookEntity.getBookNumber(), bookEntity.getFinished());
    }
}
