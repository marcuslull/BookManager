package com.marcuslull.bookmanager.entities;

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
public abstract class ApiResponseEntity {
    protected Date timestamp;
    protected String requestId;
    protected String clientIp;
    protected String method;
    protected String path;
    protected String status;

    protected ApiResponseEntity(String status, HttpServletRequest request) {
        this.timestamp  = Date.from(Instant.now());
        this.requestId = request.getRequestId();
        this.clientIp = request.getRemoteAddr();
        this.method = request.getMethod();
        this.path = request.getRequestURI();
        this.status = status;

        logResponse();
    }

    private void logResponse() {
        log.info("{}", this);
    }
}