package com.marcuslull.bookmanager.handlers;

import com.marcuslull.bookmanager.exceptions.DefensiveNullException;
import com.marcuslull.bookmanager.exceptions.DuplicateEntityException;
import com.marcuslull.bookmanager.exceptions.RequestLimitExceededException;
import com.marcuslull.bookmanager.responses.ApiResponse;
import com.marcuslull.bookmanager.responses.UnexpectedExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * GlobalExceptionHandler handles various exceptions thrown within the application and provides standardized API responses.
 *
 * <p>This class is annotated with {@link RestControllerAdvice} to allow exception handling across the whole application.
 * It uses {@code @Slf4j} for logging the exception details and constructing proper response entities for the end-users.</p>
 *
 * <ul>
 *     <li>{@link #handleHttpMessageNotReadableException(Exception)} - Handles malformed JSON or missing request bodies.</li>
 *     <li>{@link #handleDefensiveNullException(Exception)} - Handles {@link DefensiveNullException} indicating unexpected null arguments.</li>
 *     <li>{@link #handleRequestLimitExceededException(Exception)} - Handles rate limiting by returning status 429.</li>
 *     <li>{@link #handleDuplicateEntityException(Exception)} - Handles {@link DuplicateEntityException} indicating duplicate entities.</li>
 *     <li>{@link #handleNoResourceFoundException(NoResourceFoundException)} - Handles resource not found situations.</li>
 *     <li>{@link #handleException(Exception)} - Catches all other exceptions not explicitly handled.</li>
 * </ul>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the {@link HttpMessageNotReadableException} which is thrown when an HTTP message cannot be read.
     * This typically happens due to a malformed JSON or missing request body.
     * It logs the exception details and returns a response entity with a status of 400 BAD REQUEST.
     *
     * @param ex The exception that occurred while reading the HTTP message.
     * @return A {@link ResponseEntity} containing the status of the error and a detailed {@link ApiResponse}.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleHttpMessageNotReadableException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("HTTP message body not readable", getRequest()));
    }

    /**
     * Handles the {@link DefensiveNullException} which is thrown when an assumed non-null controller argument is found to be null.
     * This method logs the exception details and returns a response entity with a status of 500 INTERNAL SERVER ERROR.
     *
     * <p>The response entity contains a detailed {@link ApiResponse} that provides information about the error and the request.
     *
     * @param ex The exception that occurred, specifically an instance of {@link DefensiveNullException}.
     * @return A {@link ResponseEntity} containing the HTTP status of the error and a detailed {@link ApiResponse}.
     */
    @ExceptionHandler(DefensiveNullException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleDefensiveNullException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An unexpected error occurred", getRequest()));
    }

    /**
     * Handles the RequestLimitExceededException and returns a response entity with status code 429 (Too Many Requests).
     *
     * <p>This method logs the exception and formats a response entity containing the exception's message and the current request information.</p>
     *
     * @param ex The exception that was thrown. Expected to be of type RequestLimitExceededException.
     * @return ResponseEntity containing an error message and request details, with HTTP status 429.
     */
    @ExceptionHandler(RequestLimitExceededException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity<?> handleRequestLimitExceededException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ApiResponse(ex.getMessage(), getRequest()));
    }

    /**
     * Handles exceptions of type DuplicateEntityException.
     * <p>
     * This method is responsible for handling exceptions that occur due to duplicate entities.
     * It logs the exception and returns a standardized API response.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing an ApiResponse with a conflict status and a message
     */
    @ExceptionHandler(DuplicateEntityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> handleDuplicateEntityException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ex.getMessage(), getRequest()));
    }

    /**
     * Handles exceptions of type NoResourceFoundException and MethodArgumentTypeMismatchException.
     *
     * <p>This method is executed when the specified exceptions are thrown. It logs the exception
     * and returns a ResponseEntity object with an HTTP status of NOT_FOUND (404) and a body
     * containing an UnexpectedExceptionResponse message indicating that the resource path was not found.
     *
     * @param ex the exception that was thrown
     * @return ResponseEntity containing the HTTP status NOT_FOUND and an UnexpectedExceptionResponse message
     */
    @ExceptionHandler({NoResourceFoundException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UnexpectedExceptionResponse("Resource path not found", getRequest()));
    }

    /**
     * Handles exceptions thrown within the application and returns a standardized
     * response with HTTP status 500 (Internal Server Error).
     *
     * <p>This method logs the exception details and constructs a response body
     * containing an error message and the current request details.</p>
     *
     * @param ex The exception that was thrown.
     * @return A {@link ResponseEntity} object containing an {@link UnexpectedExceptionResponse}
     *         body with the error message and request details.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UnexpectedExceptionResponse("An unexpected error occurred", getRequest()));
    }

    private void logIt(Exception ex) {
        log.error(ex.getMessage(), ex);
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
}
