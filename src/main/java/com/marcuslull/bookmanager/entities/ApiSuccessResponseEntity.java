package com.marcuslull.bookmanager.entities;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiSuccessResponseEntity<T> extends ApiResponseEntity {
    private T data;

    public ApiSuccessResponseEntity(HttpServletRequest request, T data) {
        super("Success", request);
        this.data = data;
    }
}
