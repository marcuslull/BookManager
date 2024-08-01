package com.marcuslull.bookmanager.controllers;

import com.marcuslull.bookmanager.dtos.BookDto;
import com.marcuslull.bookmanager.entities.BookEntity;
import com.marcuslull.bookmanager.responses.ApiResponse;
import com.marcuslull.bookmanager.responses.PostErrorResponse;
import com.marcuslull.bookmanager.responses.SuccessResponse;
import com.marcuslull.bookmanager.services.BookService;
import com.marcuslull.bookmanager.services.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;
    private final RateLimitService rateLimitService;

    public BookController(BookService bookService, RateLimitService rateLimitService) {
        this.bookService = bookService;
        this.rateLimitService = rateLimitService;
    }

    @GetMapping("/books")
    public ResponseEntity<?> getBooks(HttpServletRequest request) {
        return (rateLimitService.isLimited(request)) ?
                ResponseEntity.status(429).body(new ApiResponse("Too Many Requests", request)) :
                ResponseEntity.status(200).body(new SuccessResponse<>(request, bookService.findAll()));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(HttpServletRequest request, @PathVariable Long id) {
        if (rateLimitService.isLimited(request)) {
            return ResponseEntity.status(429).body(new ApiResponse("Too Many Requests", request));
        }
        BookEntity bookEntity = bookService.findById(id);
        return (bookEntity == null) ?
                ResponseEntity.status(404).body(new ApiResponse("Not Found", request)) :
                ResponseEntity.status(200).body(new SuccessResponse<>(request, bookEntity));
    }

    @PostMapping("/books")
    public ResponseEntity<?> postBooks(HttpServletRequest request, @Valid @RequestBody List<BookDto> bookDtos, BindingResult bindingResult) {
        if (rateLimitService.isLimited(request)) {
            return ResponseEntity.status(429).body(new ApiResponse("Too Many Requests", request));
        }
        return (bindingResult.hasErrors()) ?
                ResponseEntity.status(400).body(new PostErrorResponse(request, bindingResult.getAllErrors())) :
                ResponseEntity.status(201).body(new SuccessResponse<>(request, bookService.saveAll(bookDtos)));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(HttpServletRequest request, @PathVariable Long id) {
        if (rateLimitService.isLimited(request)) {
            return ResponseEntity.status(429).body(new ApiResponse("Too Many Requests", request));
        }
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
