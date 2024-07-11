package com.marcuslull.bookmanager.controllers;

import com.marcuslull.bookmanager.dtos.BookDto;
import com.marcuslull.bookmanager.entities.Book;
import com.marcuslull.bookmanager.repositories.BookRepository;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public ApiResponse getBooks() {
        Link link = linkTo(BookController.class).withSelfRel();
        return new ApiSuccessResponse<>(link, bookRepository.findAll());
    }

    @PostMapping("/books")
    public ApiResponse postBook(@RequestBody List<BookDto> bookDtos) {
        Link link = linkTo(BookController.class).withSelfRel();
        List<Book> books = bookDtos.stream().map(Book::fromDTO).toList();
        return new ApiSuccessResponse<>(link, bookRepository.saveAll(books));
    }
}
