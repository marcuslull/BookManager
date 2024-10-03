package com.marcuslull.bookmanager.dtos;

import java.util.List;

/**
 * <p>
 * Represents a page of data in a paginated response.
 * </p>
 * <ul>
 *     <li>{@code totalPages} &ndash; the total number of pages available</li>
 *     <li>{@code totalElements} &ndash; the total number of elements available</li>
 *     <li>{@code pageNumber} &ndash; the current page number</li>
 *     <li>{@code pageSize} &ndash; the number of elements per page</li>
 *     <li>{@code firstPage} &ndash; indicates if the current page is the first one</li>
 *     <li>{@code lastPage} &ndash; indicates if the current page is the last one</li>
 *     <li>{@code content} &ndash; the list of content elements within the current page</li>
 * </ul>
 */
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
