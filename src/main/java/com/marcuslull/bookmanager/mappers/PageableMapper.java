package com.marcuslull.bookmanager.mappers;

import com.marcuslull.bookmanager.dtos.PageOfBookEntitiesDto;
import com.marcuslull.bookmanager.entities.BookEntity;
import org.springframework.data.domain.Page;

public class PageableMapper {
    public static PageOfBookEntitiesDto pageableToPageableBookDto(Page<BookEntity> page) {
        return new PageOfBookEntitiesDto(
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
