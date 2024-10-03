package com.marcuslull.bookmanager.services;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for a service that handles HTTP request rate limiting.
 *
 * <p>This service provides the capability to check if a particular HTTP request
 * is subject to rate limiting. Implementations of this interface should define
 * the specific logic to determine if a request exceeds the defined rate limits.</p>
 */
public interface RateLimitService {

    /**
     * Checks if the given HTTP request exceeds the allowed rate limits.
     *
     * <p>This method uses predefined rules and thresholds to determine whether
     * the request should be rate-limited. If the request exceeds the defined
     * rate limits, the method returns {@code true}; otherwise, it returns {@code false}.</p>
     *
     * @param request the {@code HttpServletRequest} object representing the client's request.
     *                This parameter provides access to request information such as headers,
     *                parameters, and request URI.
     *
     * @return {@code true} if the request is rate-limited, {@code false} otherwise.
     */
    boolean isLimited(HttpServletRequest request);
}
