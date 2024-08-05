
package com.marcuslull.bookmanager.mappers;

import static org.junit.jupiter.api.Assertions.*;
import com.marcuslull.bookmanager.dtos.PageDto;
import com.marcuslull.bookmanager.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import org.junit.jupiter.api.Test;

class PageableMapperTest {

    @Test
    void testPageableToPageDto() {
        BookEntity bookEntity = new BookEntity("test", "test", 200, 1, false);
        List<BookEntity> bookList = List.of(bookEntity);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BookEntity> bookPage = new PageImpl<>(bookList, pageRequest, 1);

        PageDto result = PageableMapper.pageableToPageDto(bookPage);

        assertEquals(1, result.totalPages());
        assertEquals(1, result.totalElements());
        assertEquals(0, result.pageNumber());
        assertEquals(1, result.totalElements());
        assertTrue(result.firstPage());
        assertTrue(result.lastPage());
        assertEquals(bookList, result.content());
    }
}
