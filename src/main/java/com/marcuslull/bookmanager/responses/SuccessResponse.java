package com.marcuslull.bookmanager.responses;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse<T> extends ApiResponse {
    private T data;

    public SuccessResponse(HttpServletRequest request, T data) {
        super("Success", request);
        this.data = data;
    }
}
