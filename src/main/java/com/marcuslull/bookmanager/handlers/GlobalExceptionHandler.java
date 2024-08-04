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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DefensiveNullException.class)
    public ResponseEntity<?> handleDefensiveNullException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An unexpected error occurred", getRequest()));
    }

    @ExceptionHandler(RequestLimitExceededException.class)
    public ResponseEntity<?> handleRequestLimitExceededException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ApiResponse(ex.getMessage(), getRequest()));
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<?> handleDuplicateEntityException(Exception ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(ex.getMessage(), getRequest()));
    }

    @ExceptionHandler({NoResourceFoundException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException ex) {
        logIt(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UnexpectedExceptionResponse("Resource path not found", getRequest()));
    }

    @ExceptionHandler(Exception.class)
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
