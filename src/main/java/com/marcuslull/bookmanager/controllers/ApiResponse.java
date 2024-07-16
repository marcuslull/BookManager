package com.marcuslull.bookmanager.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public abstract class ApiResponse {
    protected Date timestamp = Date.from(Instant.now());
    protected String method;
    protected String path;
    protected String status;

    protected ApiResponse(String status, HttpServletRequest request) {
        this.method = request.getMethod();
        this.path = request.getRequestURI();
        this.status = status;
    }
}
