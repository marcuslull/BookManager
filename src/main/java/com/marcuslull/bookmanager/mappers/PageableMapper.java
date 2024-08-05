package com.marcuslull.bookmanager.mappers;

import com.marcuslull.bookmanager.dtos.PageDto;
import com.marcuslull.bookmanager.entities.BookEntity;
import org.springframework.data.domain.Page;

public class PageableMapper {
    public static PageDto pageableToPageDto(Page<BookEntity> page) {
        return new PageDto(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getNumberOfElements(),
                page.isFirst(),
                page.isLast(),
                page.getContent()
        );
    }
}
