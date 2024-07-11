package com.marcuslull.bookmanager.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class ApiSuccessResponse<T> implements ApiResponse {
    private Date timestamp = Date.from(Instant.now());
    private Link path;
    private String status = "success";
    private T data;

    public ApiSuccessResponse(Link link, T data) {
        this.path = link;
        this.data = data;
    }
}
