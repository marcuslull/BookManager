package com.marcuslull.bookmanager.controllers;

import com.marcuslull.bookmanager.dtos.BookDto;
import com.marcuslull.bookmanager.entities.ApiPostErrorResponseEntity;
import com.marcuslull.bookmanager.entities.ApiResponseEntity;
import com.marcuslull.bookmanager.entities.ApiSuccessResponseEntity;
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
        return ResponseEntity.status(200).body(new ApiSuccessResponseEntity<>(request, bookRepository.findAll()));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(HttpServletRequest request, @PathVariable Long id) {
        BookEntity bookEntity = bookRepository.findById(id).orElse(null);
        return (bookEntity == null) ?
                ResponseEntity.status(404).body(new ApiResponseEntity("Not Found", request)) :
                ResponseEntity.status(200).body(new ApiSuccessResponseEntity<>(request, bookEntity));
    }

    @PostMapping("/books")
    public ResponseEntity<?> postBooks(HttpServletRequest request, @Valid @RequestBody List<BookDto> bookDtos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiPostErrorResponseEntity(request, bindingResult.getAllErrors()));
        }
        List<BookEntity> bookEntities = bookDtos.stream().map(BookEntity::fromDTO).toList();
        return ResponseEntity.status(200).body(new ApiSuccessResponseEntity<>(request, bookRepository.saveAll(bookEntities)));
    }
}
