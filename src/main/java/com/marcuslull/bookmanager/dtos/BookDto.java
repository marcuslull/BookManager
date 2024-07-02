package com.marcuslull.bookmanager.dtos;


import java.io.Serializable;

/**
 * DTO for {@link com.marcuslull.bookmanager.entities.Book}
 */
public record BookDto(String title, Integer pages, Integer bookNumber, Boolean finished) implements Serializable {
}