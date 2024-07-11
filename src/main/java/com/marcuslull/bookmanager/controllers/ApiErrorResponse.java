package com.marcuslull.bookmanager.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponse implements ApiResponse {
    private String status = "error";
    private String message;

    public ApiErrorResponse(String message) {
        this.message = message;
    }
}
