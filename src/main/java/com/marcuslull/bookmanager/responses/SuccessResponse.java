package com.marcuslull.bookmanager.responses;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a success response that includes additional data along with the standard API response information.
 *
 * <p>This class extends {@link ApiResponse} and adds a data payload to the response, which can be any type of object.
 * It is typically used when the API request is successful and the client is expected to receive additional data
 * as part of the response body.</p>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Includes all standard fields from {@link ApiResponse} such as timestamp, requestId, clientIp, method, path, and status.</li>
 *   <li>Additional field: <b>data</b> - This field holds the specific data payload related to the successful response. It can be of any type.</li>
 * </ul>
 *
 * <p>The constructor of this class initializes the standard API response fields using the superclass {@link ApiResponse}
 * and additionally sets the <b>data</b> field.</p>
 *
 * @param <T> the type of the data payload in the success response
 */
@Getter
@Setter
public class SuccessResponse<T> extends ApiResponse {
    private T data;

    public SuccessResponse(HttpServletRequest request, T data) {
        super("Success", request);
        this.data = data;
    }
}
