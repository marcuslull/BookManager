package com.marcuslull.bookmanager.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Date;

@Slf4j
@Getter
@Setter
@ToString
public abstract class ApiResponse {
    protected Date timestamp;
    protected String requestId;
    protected String clientIp;
    protected String method;
    protected String path;
    protected String status;

    protected ApiResponse(String status, HttpServletRequest request) {
        this.timestamp  = Date.from(Instant.now());
        this.requestId = request.getRequestId();
        this.clientIp = request.getRemoteAddr();
        this.method = request.getMethod();
        this.path = request.getRequestURI();
        this.status = status;

        logRequest();
    }

    private void logRequest() {
        log.info("{}", this);
    }
}
