package com.marcuslull.bookmanager.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiSuccessResponse<T> extends ApiResponse {
    private T data;

    public ApiSuccessResponse(HttpServletRequest request, T data) {
        super("Success", request);
        this.data = data;
    }
}
