package com.marcuslull.bookmanager.responses;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an API response for handling field validation errors encountered during POST requests.
 * <p>
 * This class extends the {@code ApiResponse} class to include:
 * <ul>
 *   <li><b>errorMessages</b>: A list of error messages describing the validation issues found.</li>
 * </ul>
 * </p>
 * <p>
 * This class provides a standardized format for returning validation error information to the client.
 * It extracts error messages from the provided list of {@code ObjectError} instances.
 * </p>
 * <p>
 * Usage: This class is particularly useful when validating user inputs in a POST request, such as
 * creating or updating resources, and responding with detailed validation errors.
 * </p>
 *
 * @see ApiResponse
 * @see FieldError
 * @see ObjectError
 */
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
