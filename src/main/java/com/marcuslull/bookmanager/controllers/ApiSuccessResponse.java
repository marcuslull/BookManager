package com.marcuslull.bookmanager.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

@Getter
@Setter
public class ApiSuccessResponse<T> extends ApiResponse {
    private T data;

    public ApiSuccessResponse(Link path, T data) {
        super("Success", path);
        this.data = data;
    }
}
