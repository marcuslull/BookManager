package com.marcuslull.bookmanager.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public abstract class ApiResponse {
    protected Date timestamp = Date.from(Instant.now());
    protected String status;
    protected Link path;

    protected ApiResponse(String status, Link path) {
        this.status = status;
        this.path = path;
    }
}
