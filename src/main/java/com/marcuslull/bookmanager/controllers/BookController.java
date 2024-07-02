package com.marcuslull.bookmanager.controllers;

import com.marcuslull.bookmanager.dtos.BookDto;
import com.marcuslull.bookmanager.entities.Book;
import com.marcuslull.bookmanager.repositories.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public Iterable<Book> getBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/books")
    public Iterable<Book> postBook(@RequestBody List<BookDto> bookDtos) {
        List<Book> books = bookDtos.stream().map(Book::fromDTO).toList();
        return bookRepository.saveAll(books);
    }
}
