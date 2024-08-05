
package com.marcuslull.bookmanager.controllers;

import com.marcuslull.bookmanager.dtos.BookDto;
import com.marcuslull.bookmanager.dtos.PageDto;
import com.marcuslull.bookmanager.entities.BookEntity;
import com.marcuslull.bookmanager.responses.ApiResponse;
import com.marcuslull.bookmanager.responses.PostFieldErrorResponse;
import com.marcuslull.bookmanager.responses.SuccessResponse;
import com.marcuslull.bookmanager.services.BookService;
import com.marcuslull.bookmanager.services.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private RateLimitService rateLimitService;

    private List<BookDto> bookDtoList;
    private HttpServletRequest request;
    private Pageable pageable;
    private PageDto pageDto;
    private BookDto bookDto;
    private BookEntity bookEntity;
    private Long id;
    private List<BookEntity> bookEntityList;
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
        pageable = Mockito.mock(Pageable.class);

        id = 1L;
        bookDto = new BookDto("title", "author", 1, 2, true);
        bookDtoList = List.of(bookDto);
        pageDto = new PageDto(1, 1L, 0, 5, true, false, bookDtoList);
        bookEntity = new BookEntity();
        bookEntityList = List.of(bookEntity);
        bindingResult = new BeanPropertyBindingResult(bookDtoList, "bookDtos");
    }

    @Test
    public void testGetBooks_HappyPath() {
        when(bookService.findAllPaged(pageable)).thenReturn(pageDto);
        when(rateLimitService.isLimited(request)).thenReturn(false);

        ResponseEntity<?> responseEntity = bookController.getBooks(request, pageable);

        assertEquals(200, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(SuccessResponse.class, responseEntity.getBody());
        assertEquals(pageDto, ((SuccessResponse<PageDto>) responseEntity.getBody()).getData());
    }
    @Test
    public void testGetBook_HappyPath() {
        when(bookService.findById(id)).thenReturn(bookEntity);
        when(rateLimitService.isLimited(request)).thenReturn(false);

        ResponseEntity<?> responseEntity = bookController.getBook(request, id);

        assertEquals(200, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(SuccessResponse.class, responseEntity.getBody());
        assertEquals(bookEntity, ((SuccessResponse<BookEntity>) responseEntity.getBody()).getData());
    }

    @Test
    public void testGetBook_NotFound() {
        when(bookService.findById(id)).thenReturn(null);
        when(rateLimitService.isLimited(request)).thenReturn(false);

        ResponseEntity<?> responseEntity = bookController.getBook(request, id);

        assertEquals(404, responseEntity.getStatusCode().value());
        assertInstanceOf(ApiResponse.class, responseEntity.getBody());
        assertEquals("Not Found", ((ApiResponse) responseEntity.getBody()).getStatus());
    }
    @Test
    public void testPostBooks_HappyPath() {
        when(bookService.saveAll(bookDtoList)).thenReturn(bookEntityList);
        when(rateLimitService.isLimited(request)).thenReturn(false);

        ResponseEntity<?> responseEntity = bookController.postBooks(request, bookDtoList, bindingResult);

        assertEquals(201, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(SuccessResponse.class, responseEntity.getBody());
        assertEquals(bookEntityList, ((SuccessResponse<List<BookEntity>>) responseEntity.getBody()).getData());
    }

    @Test
    public void testPostBooks_FieldErrors() {
        when(rateLimitService.isLimited(request)).thenReturn(false);
        bindingResult.rejectValue(null, "");

        ResponseEntity<?> responseEntity = bookController.postBooks(request, bookDtoList, bindingResult);

        assertEquals(400, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(PostFieldErrorResponse.class, responseEntity.getBody());
    }

    @Test
    public void testDeleteBook_HappyPath() {
        when(rateLimitService.isLimited(request)).thenReturn(false);

        ResponseEntity<?> responseEntity = bookController.deleteBook(request, id);

        assertEquals(204, responseEntity.getStatusCode().value());
    }

    @Test
    public void testDeleteBook_NotFound() {
        when(rateLimitService.isLimited(request)).thenReturn(false);

        ResponseEntity<?> responseEntity = bookController.deleteBook(request, id);

        assertEquals(204, responseEntity.getStatusCode().value());
    }
}
