package com.marcuslull.bookmanager.entities;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApiErrorResponseEntity extends ApiResponseEntity {
    private List<String> errorMessages;

    public ApiErrorResponseEntity(HttpServletRequest request, List<ObjectError> errors) {
        super("Error", request);
        this.errorMessages = new ArrayList<>();
        for (ObjectError error : errors) {
            if (error instanceof FieldError fieldError) {
                String field = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                this.errorMessages.add(field + " " + defaultMessage);
            } else {
                this.errorMessages.add(error.getDefaultMessage());
            }
        }
    }
}