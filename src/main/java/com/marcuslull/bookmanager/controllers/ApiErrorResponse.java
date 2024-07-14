package com.marcuslull.bookmanager.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApiErrorResponse extends ApiResponse {
    private List<String> errorMessages;

    public ApiErrorResponse(Link path, List<ObjectError> errors) {
        super("Error", path);
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
