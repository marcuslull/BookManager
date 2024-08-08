package com.marcuslull.bookmanager.dtos;


import com.marcuslull.bookmanager.entities.BookEntity;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link BookEntity}
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