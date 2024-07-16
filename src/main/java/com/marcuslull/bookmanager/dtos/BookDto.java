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
        @NotNull @Size(min = 2, max = 50) String title,
        @NotNull @Digits(integer = 4, fraction = 0) @Positive Integer pages,
        @NotNull @Digits(integer = 2, fraction = 0)
            @Positive(message = "must be > 0. Single books should be book 1") Integer bookNumber,
        Boolean finished
) implements Serializable {}