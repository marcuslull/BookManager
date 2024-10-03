package com.marcuslull.bookmanager.dtos;


import com.marcuslull.bookmanager.entities.BookEntity;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * Data transfer object representing a book.
 * <p>
 * This class is used for transferring book data across various layers of the application,
 * such as controllers and services. The fields are validated using the Java Bean Validation API.
 * </p>
 *
 * <p><b>Field constraints:</b></p>
 * <ul>
 *   <li><b>title</b>: must not be null and its length must be between {@value #MIN_STRING_LENGTH} and {@value #MAX_STRING_LENGTH} characters.</li>
 *   <li><b>author</b>: must not be null and its length must be between {@value #MIN_STRING_LENGTH} and {@value #MAX_STRING_LENGTH} characters.</li>
 *   <li><b>pages</b>: must not be null, must be a positive integer, and must have up to {@value #PAGES_MAX_LENGTH} digits.</li>
 *   <li><b>bookNumber</b>: must not be null, must be a positive integer with a custom message if invalid, and must have up to {@value #BOOK_NUMBER_MAX_LENGTH} digits.</li>
 *   <li><b>finished</b>: an optional boolean indicating whether the book has been finished.</li>
 * </ul>
 *
 * <p>
 * This record implements {@link Serializable} to allow book instances to be serialized,
 * facilitating their transfer between various application layers or systems.
 * </p>
 *
 * <p><b>Constants:</b></p>
 * <ul>
 *   <li>{@code MIN_STRING_LENGTH}: Minimum length for string fields <i>(value: 2)</i>.</li>
 *   <li>{@code MAX_STRING_LENGTH}: Maximum length for string fields <i>(value: 50)</i>.</li>
 *   <li>{@code PAGES_MAX_LENGTH}: Maximum length for pages field <i>(value: 4)</i>.</li>
 *   <li>{@code BOOK_NUMBER_MAX_LENGTH}: Maximum length for book number field <i>(value: 2)</i>.</li>
 *   <li>{@code DIGITS_FRACTION}: Fraction part for integer fields <i>(value: 0)</i>.</li>
 *   <li>{@code POSITIVE_MESSAGE}: Custom message for positive validation <i>(value: "must be > 0. Single books should be book 1")</i>.</li>
 * </ul>
 */
public record BookDto(
        @NotNull @Size(min = MIN_STRING_LENGTH, max = MAX_STRING_LENGTH)
        String title,

        @NotNull @Size(min = MIN_STRING_LENGTH, max = MAX_STRING_LENGTH)
        String author,

        @NotNull @Digits(integer = PAGES_MAX_LENGTH, fraction = DIGITS_FRACTION) @Positive
        Integer pages,

        @NotNull @Digits(integer = BOOK_NUMBER_MAX_LENGTH, fraction = DIGITS_FRACTION) @Positive(message = POSITIVE_MESSAGE)
        Integer bookNumber,

        Boolean finished
) implements Serializable {
    private static final int MIN_STRING_LENGTH = 2;
    private static final int MAX_STRING_LENGTH = 50;
    private static final int PAGES_MAX_LENGTH = 4;
    private static final int BOOK_NUMBER_MAX_LENGTH = 2;
    private static final int DIGITS_FRACTION = 0;
    private static final String POSITIVE_MESSAGE = "must be > 0. Single books should be book 1";
}