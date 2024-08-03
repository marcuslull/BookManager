package com.marcuslull.bookmanager.dtos;

import com.marcuslull.bookmanager.entities.BookEntity;

import java.util.List;

public record PageOfBookEntitiesDto(
        int totalPages,
        Long totalElements,
        int pageNumber,
        int pageSize,
        boolean firstPage,
        boolean lastPage,
        List<BookEntity> content
) {
}
