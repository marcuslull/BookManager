package com.marcuslull.bookmanager.responses;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Date;

/**
 * Represents a standardized API response with commonly needed information for logging and debugging purposes.
 * <p>
 * This class includes:
 * <ul>
 *   <li><b>timestamp</b>: The time when the response was created.</li>
 *   <li><b>requestId</b>: Unique identifier for the request.</li>
 *   <li><b>clientIp</b>: IP address of the client making the request.</li>
 *   <li><b>method</b>: HTTP method of the request (e.g., GET, POST).</li>
 *   <li><b>path</b>: The request URI path.</li>
 *   <li><b>status</b>: Status message indicating the result of the request.</li>
 * </ul>
 *
 * <p>This class is primarily used to log API responses and provide consistency in the API response format.
 * Subclasses can extend this class to include additional information specific to different types of responses.
 * </p>
 *
 * <p><b>Example of subclass usage:</b> A success response might include additional data, while an error response
 * might include a list of validation errors.
 * </p>
 */
@Slf4j
@Getter
@Setter
@ToString
public class ApiResponse {
    protected Date timestamp;
    protected String requestId;
    protected String clientIp;
    protected String method;
    protected String path;
    protected String status;

    public ApiResponse(String status, HttpServletRequest request) {
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
