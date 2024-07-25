package com.marcuslull.bookmanager.controllers;

import com.marcuslull.bookmanager.dtos.BookDto;
import com.marcuslull.bookmanager.responses.PostErrorResponse;
import com.marcuslull.bookmanager.responses.ApiResponse;
import com.marcuslull.bookmanager.responses.SuccessResponse;
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
    public ResponseEntity<?> getBooks(HttpServletRequest request) {
        return ResponseEntity.status(200).body(new SuccessResponse<>(request, bookRepository.findAll()));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(HttpServletRequest request, @PathVariable Long id) {
        BookEntity bookEntity = bookRepository.findById(id).orElse(null);
        return (bookEntity == null) ?
                ResponseEntity.status(404).body(new ApiResponse("Not Found", request)) :
                ResponseEntity.status(200).body(new SuccessResponse<>(request, bookEntity));
    }

    @PostMapping("/books")
    public ResponseEntity<?> postBooks(HttpServletRequest request, @Valid @RequestBody List<BookDto> bookDtos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(400).body(new PostErrorResponse(request, bindingResult.getAllErrors()));
        }
        List<BookEntity> bookEntities = bookDtos.stream().map(BookEntity::fromDto).toList();
        return ResponseEntity.status(201).body(new SuccessResponse<>(request, bookRepository.saveAll(bookEntities)));
    }
}
