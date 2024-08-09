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

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleHttpMessageNotReadableException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("HTTP message body not readable", getRequest()));
    }

    @ExceptionHandler(DefensiveNullException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleDefensiveNullException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An unexpected error occurred", getRequest()));
    }

    @ExceptionHandler(RequestLimitExceededException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity<?> handleRequestLimitExceededException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ApiResponse(ex.getMessage(), getRequest()));
    }

    @ExceptionHandler(DuplicateEntityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> handleDuplicateEntityException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ex.getMessage(), getRequest()));
    }

    @ExceptionHandler({NoResourceFoundException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UnexpectedExceptionResponse("Resource path not found", getRequest()));
    }

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
