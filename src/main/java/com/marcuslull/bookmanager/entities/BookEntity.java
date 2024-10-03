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

/**
 * Represents an entity for a book in the database.
 * <p>
 * This entity maps to the "books" table and includes various fields with validation constraints
 * to ensure data integrity and compliance with business rules.
 * </p>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li><b>id</b>: The unique identifier for the book entity, generated automatically.</li>
 *   <li><b>title</b>: The title of the book, which must be a non-null string of 2 to 50 characters.</li>
 *   <li><b>author</b>: The author of the book, which must be a non-null string of 3 to 50 characters.</li>
 *   <li><b>pages</b>: The number of pages in the book, which must be a non-null positive integer with up to 4 digits.</li>
 *   <li><b>bookNumber</b>: The book number, which must be a non-null positive integer with up to 2 digits.</li>
 *   <li><b>finished</b>: An optional boolean indicating whether the book has been finished.</li>
 *   <li><b>dedupeId</b>: A string used for deduplication purposes, combining the title and pages.</li>
 * </ul>
 *
 * <p><b>Constructors:</b></p>
 * <ul>
 *   <li><b>BookEntity()</b>: Default constructor.</li>
 *   <li><b>BookEntity(String title, String author, Integer pages, Integer bookNumber, Boolean finished)</b>: Constructor to initialize a new book entity with specific values.</li
 * >
 * </ul>
 *
 * <p><b>Methods:</b></p>
 * <ul>
 *   <li><b>fromDto(@Valid BookDto bookDto)</b>: Creates a new BookEntity from a BookDto object.</li>
 *   <li><b>toDto(@Valid BookEntity bookEntity)</b>: Converts a BookEntity to a BookDto object.</li>
 * </ul>
 *
 * <p>
 * This entity is annotated with JPA annotations such as {@code @Entity}, {@code @Id}, {@code @GeneratedValue},
 * and {@code @Column} to map class fields to database columns and enforce unique constraints.
 * </p>
 */
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

    /**
     * Converts a {@link BookDto} object to a {@link BookEntity}.
     *
     * @param bookDto <p>The data transfer object representing a book. It contains the following fields:
     * <ul>
     *   <li>title - The title of the book</li>
     *   <li>author - The author of the book</li>
     *   <li>pages - The number of pages in the book</li>
     *   <li>bookNumber - A unique number identifying the book</li>
     *   <li>finished - A boolean indicating if the book has been finished</li>
     * </ul>
     * (<code>bookDto</code> must be valid and not null).</p>
     *
     * @return <p>A new {@link BookEntity} instance with the corresponding fields from the provided {@link BookDto}.</p>
     */
    public static BookEntity fromDto(@Valid BookDto bookDto) {
        return new BookEntity(bookDto.title(), bookDto.author(), bookDto.pages(), bookDto.bookNumber(), bookDto.finished());
    }

    /**
     * Converts a {@link BookEntity} object to a {@link BookDto}.
     *
     * @param bookEntity
     * <p>The entity object representing a book. It contains the following fields:</p>
     * <ul>
     *   <li><code>title</code> - The title of the book.</li>
     *   <li><code>author</code> - The author of the book.</li>
     *   <li><code>pages</code> - The number of pages in the book.</li>
     *   <li><code>bookNumber</code> - A unique number identifying the book.</li>
     *   <li><code>finished</code> - A boolean indicating if the book has been finished.</li>
     * </ul>
     * <p><code>bookEntity</code> must be valid and not null.</p>
     *
     * @return
     * <p>A new {@link BookDto} instance with the corresponding fields from the provided {@link BookEntity}.</p>
     */
    public static BookDto toDto(@Valid BookEntity bookEntity) {
        return new BookDto(bookEntity.getTitle(), bookEntity.getAuthor(), bookEntity.getPages(), bookEntity.getBookNumber(), bookEntity.getFinished());
    }
}
