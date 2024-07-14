package com.marcuslull.bookmanager.dtos;


import com.marcuslull.bookmanager.entities.Book;
import jakarta.validation.constraints.*;

import java.io.Serializable;

/**
 * DTO for {@link Book}
 */
public record BookDto(
        @NotNull @Size(min = 2, max = 50) String title,
        @NotNull @Digits(integer = 4, fraction = 0) @Positive Integer pages,
        @NotNull @Digits(integer = 2, fraction = 0) @Positive Integer bookNumber,
        Boolean finished
) implements Serializable {}