package com.marcuslull.bookmanager.responses;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Represents a specific type of API response that is used when an unexpected exception occurs.
 *
 * <p>This class extends the {@link ApiResponse} class to include all the standard information
 * provided in an API response, such as timestamp, requestId, clientIp, method, path, and status.
 * It is intended to provide a consistent format for logging and debugging unexpected exceptions
 * encountered during API request processing.
 *
 * <p>When an instance of this class is created, the details of the request and the response status
 * are automatically logged for debugging purposes.
 *
 * <p><b>Inherited Properties:</b>
 * <ul>
 *   <li><b>timestamp</b>: The time when the response was created.</li>
 *   <li><b>requestId</b>: Unique identifier for the request.</li>
 *   <li><b>clientIp</b>: IP address of the client making the request.</li>
 *   <li><b>method</b>: HTTP method of the request (e.g., GET, POST).</li>
 *   <li><b>path</b>: The request URI path.</li>
 *   <li><b>status</b>: Status message indicating the result of the request.</li>
 * </ul>
 */
public class UnexpectedExceptionResponse extends ApiResponse {

    public UnexpectedExceptionResponse(String status, HttpServletRequest request) {
        super(status, request);
    }
}
