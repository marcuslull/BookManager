package com.marcuslull.bookmanager.mappers;

import com.marcuslull.bookmanager.dtos.PageDto;
import com.marcuslull.bookmanager.entities.BookEntity;
import org.springframework.data.domain.Page;

/**
 * <p>
 * A utility class for mapping Pageable content into a PageDto.
 * </p>
 *
 * <p>
 * Provides functionality to transform a paginated list of {@link BookEntity} objects into a {@link PageDto}.
 * </p>
 *
 * <ul>
 *     <li>Converts the total number of pages, total elements, current page number,
 *     number of elements on the current page, and whether it is the first or last page along with the content.</li>
 * </ul>
 */
public class PageableMapper {

    /**
     * Converts a {@link Page} of {@link BookEntity} objects into a {@link PageDto}.
     *
     * <p>This method extracts pagination information and content from the provided {@link Page} object
     * and maps it to a new {@link PageDto} instance.</p>
     *
     * @param page the {@link Page} object containing {@link BookEntity} instances, along with pagination data.
     *             <ul>
     *                 <li>totalPages - the total number of available pages.</li>
     *                 <li>totalElements - the total number of available elements.</li>
     *                 <li>pageNumber - the current page number.</li>
     *                 <li>numberOfElements - the number of elements present on the current page.</li>
     *                 <li>first - a boolean indicating if this is the first page.</li>
     *                 <li>last - a boolean indicating if this is the last page.</li>
     *                 <li>content - a list of elements present on the current page.</li>
     *             </ul>
     *
     * @return a {@link PageDto} containing the paginated data from the given {@link Page} object.
     */
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
