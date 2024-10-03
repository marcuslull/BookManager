package com.marcuslull.bookmanager.exceptions;

/**
 * Exception thrown when a request exceeds the rate limit.
 *
 * <p>This exception is typically used in the context of rate limiting
 * to indicate that the client has sent too many requests in a given amount of time.</p>
 *
 * <p>It extends {@code RuntimeException}, so it is an unchecked exception that does not need
 * to be declared in a method or constructor's {@code throws} clause.</p>
 *
 * <p>Common scenarios where this exception might be used include:</p>
 * <ul>
 *     <li>API rate limiting</li>
 *     <li>Preventing abuse or overuse of resources</li>
 * </ul>
 */
public class RequestLimitExceededException extends RuntimeException {
    public RequestLimitExceededException(String message) {
        super(message);
    }
}
