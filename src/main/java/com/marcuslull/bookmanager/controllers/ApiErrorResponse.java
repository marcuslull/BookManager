package com.marcuslull.bookmanager.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.*;

@Getter
@Setter
public class ApiErrorResponse implements ApiResponse {
    private String status = "error";

    @JsonIgnore
    private List<ObjectError> errors;

    private List<String> errorMessages;

    public ApiErrorResponse(List<ObjectError> errors) {
        this.errors = errors;

        this.errorMessages = new ArrayList<>();
        for (ObjectError error : errors) {
            this.errorMessages.add(error.getDefaultMessage());
        }
    }
}
