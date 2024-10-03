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

/**
 * Controller class for handling HTTP requests related to books.
 * It is a RESTful controller located at the "/api/v1" endpoint.
 * Handles GET, POST, and DELETE methods for books.
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;
    private final RateLimitService rateLimitService;

    public BookController(BookService bookService, RateLimitService rateLimitService) {
        this.bookService = bookService;
        this.rateLimitService = rateLimitService;
    }

    /**
     * Retrieves a pageable list of books.
     *
     * @param request  the HttpServletRequest object associated with the request
     * @param pageable the Pageable object used for pagination and sorting
     * @return ResponseEntity object containing the HTTP status code and the body, which is a SuccessResponse object
     */
    @GetMapping("/books")
    public ResponseEntity<?> getBooks(HttpServletRequest request, @PageableDefault(sort = "title") Pageable pageable) {
        defensiveNullCheck(List.of(request, pageable));
        checkRateLimit(request);
        return ResponseEntity.status(200).body(new SuccessResponse<>(request, bookService.findAllPaged(pageable)));
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param request the HttpServletRequest object associated with the request
     * @param id the ID of the book to retrieve
     * @return ResponseEntity object containing the HTTP status code and the body, which is a SuccessResponse object if the book is found,
     *         or an ApiResponse object with a 404 status code if the book is not found
     */
    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(HttpServletRequest request, @PathVariable Long id) {
        defensiveNullCheck(List.of(request, id));
        checkRateLimit(request);
        BookEntity bookEntity = bookService.findById(id);
        return (bookEntity == null) ?
                ResponseEntity.status(404).body(new ApiResponse("Not Found", request)) :
                ResponseEntity.status(200).body(new SuccessResponse<>(request, bookEntity));
    }

    /**
     * Handles HTTP POST requests to '/books' endpoint.
     * Validates a list of book DTOs and saves them to the database if no validation errors are found.
     *
     * @param request        the HttpServletRequest object associated with the request
     * @param bookDtos       the list of BookDto objects to be validated and saved
     * @param bindingResult  the BindingResult object that holds the validation errors
     * @return a ResponseEntity object containing the HTTP status code and the body
     *                       - If there are validation errors, returns a ResponseEntity with 400 Bad Request status code
     *                         and a body of PostFieldErrorResponse object containing the validation error messages
     *                       - If the book DTOs are successfully saved, returns a ResponseEntity with 201 Created status code
     *                         and a body of SuccessResponse object containing the saved BookEntity objects
     */
    @PostMapping("/books")
    public ResponseEntity<?> postBooks(HttpServletRequest request, @Valid @RequestBody List<BookDto> bookDtos, BindingResult bindingResult) {
        defensiveNullCheck(List.of(request, bookDtos, bindingResult));
        checkRateLimit(request);
        return (bindingResult.hasErrors()) ?
                ResponseEntity.status(400).body(new PostFieldErrorResponse(request, bindingResult.getAllErrors())) :
                ResponseEntity.status(201).body(new SuccessResponse<>(request, bookService.saveAll(bookDtos)));
    }

    /**
     * Deletes a book with the specified ID.
     *
     * @param request the HttpServletRequest object associated with the request
     * @param id the ID of the book to delete
     * @return a ResponseEntity object with the HTTP status code indicating success of the operation
     */
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
