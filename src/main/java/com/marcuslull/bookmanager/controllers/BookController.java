package com.marcuslull.bookmanager.controllers;

import com.marcuslull.bookmanager.dtos.BookDto;
import com.marcuslull.bookmanager.entities.Book;
import com.marcuslull.bookmanager.repositories.BookRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public ApiResponse getBooks(HttpServletRequest request) {
        logRequest(request);
        return new ApiSuccessResponse<>(request, bookRepository.findAll());
    }

    @GetMapping("/books/{id}")
    public ApiResponse getBook(HttpServletRequest request, @PathVariable Long id) {
        logRequest(request);
        return new ApiSuccessResponse<>(request, bookRepository.findById(id));
    }

    @PostMapping("/books")
    public ResponseEntity<ApiResponse> postBooks(HttpServletRequest request, @Valid @RequestBody List<BookDto> bookDtos, BindingResult bindingResult) {
        logRequest(request);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiErrorResponse(request, bindingResult.getAllErrors()));
        }
        List<Book> books = bookDtos.stream().map(Book::fromDTO).toList();
        return ResponseEntity.ok().body(new ApiSuccessResponse<>(request, bookRepository.saveAll(books)));
    }

    private void logRequest(HttpServletRequest request) {
        log.info("{} on {} from {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
    }
}
