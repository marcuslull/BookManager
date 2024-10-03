package com.marcuslull.bookmanager.exceptions;

/**
 * Exception thrown when attempting to create a duplicate entity in the database.
 * <p>
 * This exception is typically used in scenarios where unique constraints need to be enforced
 * and an operation involves checking for the existence of an entity before proceeding.
 * If the entity already exists, this exception will be thrown to prevent duplicate entries.
 * </p>
 *
 * <p><b>Constructor:</b></p>
 * <ul>
 *   <li>{@code DuplicateEntityException(String message)}:
 *   Initializes a new instance of the {@code DuplicateEntityException} class with a specified error message.</li>
 * </ul>
 *
 * <p><b>Usage Information:</b></p>
 * <p>
 * This exception should be thrown when an operation cannot be completed due to the presence
 * of a duplicate entity, typically before attempting to save a new entity to the database.
 * </p>
 */
public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}
