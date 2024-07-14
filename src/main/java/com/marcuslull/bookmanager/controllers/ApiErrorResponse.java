package com.marcuslull.bookmanager.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.validation.ObjectError;

import java.util.*;

@Getter
@Setter
public class ApiErrorResponse extends ApiResponse {
    private List<String> errorMessages;

    public ApiErrorResponse(Link path, List<ObjectError> errors) {
        super("Error", path);
        this.errorMessages = new ArrayList<>();
        for (ObjectError error : errors) {
            this.errorMessages.add(error.getDefaultMessage());
        }
    }
}
