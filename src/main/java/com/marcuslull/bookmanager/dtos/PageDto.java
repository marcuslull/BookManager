package com.marcuslull.bookmanager.dtos;

import java.util.List;

public record PageDto(
        int totalPages,
        Long totalElements,
        int pageNumber,
        int pageSize,
        boolean firstPage,
        boolean lastPage,
        List<?> content
) {
}
