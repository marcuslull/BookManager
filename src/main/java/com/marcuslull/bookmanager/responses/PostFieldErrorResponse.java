package com.marcuslull.bookmanager.responses;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostFieldErrorResponse extends ApiResponse {
    private List<String> errorMessages;

    public PostFieldErrorResponse(HttpServletRequest request, List<ObjectError> errors) {
        super("Post field error", request);
        this.errorMessages = extractErrorMessages(errors);
    }

    private List<String> extractErrorMessages(List<ObjectError> errors) {
        this.errorMessages = new ArrayList<>();
        for (ObjectError error : errors) {
            if (error instanceof FieldError fieldError) {
                String field = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                this.errorMessages.add(field + " " + defaultMessage);
            } else {
                this.errorMessages.add(error.getDefaultMessage());
            }
        }
        return errorMessages;
    }
}
