package com.marcuslull.bookmanager.responses;

import jakarta.servlet.http.HttpServletRequest;

public class UnexpectedExceptionResponse extends ApiResponse {

    public UnexpectedExceptionResponse(String status, HttpServletRequest request) {
        super(status, request);
    }
}
