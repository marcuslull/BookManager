package com.marcuslull.bookmanager.controllers;

import com.marcuslull.bookmanager.dtos.BookDto;
import com.marcuslull.bookmanager.entities.BookEntity;
import com.marcuslull.bookmanager.exceptions.DefensiveNullException;
import com.marcuslull.bookmanager.exceptions.RequestLimitExceededException;
import com.marcuslull.bookmanager.responses.ApiResponse;
import com.marcuslull.bookmanager.responses.PostFieldErrorResponse;
import com.marcuslull.bookmanager.responses.SuccessResponse;
import com.marcuslull.bookmanager.services.BookService;
import com.marcuslull.bookmanager.services.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<?> getBooks(HttpServletRequest request, @PageableDefault(sort = "title") Pageable pageable) {
        // TODO: This does not display a single book in content
        defensiveNullCheck(List.of(request, pageable));
        checkRateLimit(request);
        return ResponseEntity.status(200).body(new SuccessResponse<>(request, bookService.findAllPaged(pageable)));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(HttpServletRequest request, @PathVariable Long id) {
        defensiveNullCheck(List.of(request, id));
        checkRateLimit(request);
        BookEntity bookEntity = bookService.findById(id);
        return (bookEntity == null) ?
                ResponseEntity.status(404).body(new ApiResponse("Not Found", request)) :
                ResponseEntity.status(200).body(new SuccessResponse<>(request, bookEntity));
    }

    @PostMapping("/books")
    public ResponseEntity<?> postBooks(HttpServletRequest request, @Valid @RequestBody List<BookDto> bookDtos, BindingResult bindingResult) {
        defensiveNullCheck(List.of(request, bookDtos, bindingResult));
        checkRateLimit(request);
        return (bindingResult.hasErrors()) ?
                ResponseEntity.status(400).body(new PostFieldErrorResponse(request, bindingResult.getAllErrors())) :
                ResponseEntity.status(201).body(new SuccessResponse<>(request, bookService.saveAll(bookDtos)));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(HttpServletRequest request, @PathVariable Long id) {
        defensiveNullCheck(List.of(request, id));
        checkRateLimit(request);
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void checkRateLimit(HttpServletRequest request) {
        defensiveNullCheck(List.of(request));
        if (rateLimitService.isLimited(request)) {
            throw new RequestLimitExceededException("Too Many Requests");
        }
    }

    private void defensiveNullCheck(List<Object> objectsList) {
        objectsList.forEach(object -> {
            if (object == null) {
                throw new DefensiveNullException();
            }
        });
    }
}
