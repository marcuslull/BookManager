package com.marcuslull.bookmanager.controllers;

import com.marcuslull.bookmanager.dtos.BookDto;
import com.marcuslull.bookmanager.entities.BookEntity;
import com.marcuslull.bookmanager.repositories.BookRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public ApiResponse getBooks(HttpServletRequest request) {
        return new ApiSuccessResponse<>(request, bookRepository.findAll());
    }

    @GetMapping("/books/{id}")
    public ApiResponse getBook(HttpServletRequest request, @PathVariable Long id) {
        return new ApiSuccessResponse<>(request, bookRepository.findById(id));
    }

    @PostMapping("/books")
    public ResponseEntity<ApiResponse> postBooks(HttpServletRequest request, @Valid @RequestBody List<BookDto> bookDtos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiErrorResponse(request, bindingResult.getAllErrors()));
        }
        List<BookEntity> bookEntities = bookDtos.stream().map(BookEntity::fromDTO).toList();
        return ResponseEntity.ok().body(new ApiSuccessResponse<>(request, bookRepository.saveAll(bookEntities)));
    }
}
