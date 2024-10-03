package com.marcuslull.bookmanager.exceptions;

/**
 * An exception that is thrown when an assumed non-null controller argument is found to be null.
 * This exception is used primarily for defensive programming purposes to ensure that null values
 * are not passed where they are not expected.
 *
 * <p>This runtime exception is typically leveraged in service or controller layers
 * to enforce non-null contracts on method parameters, helping to avoid potential NullPointerExceptions later in the code.
 *
 * <p>The exception message for this {@code DefensiveNullException} is preset to "Assumed controller argument is null".
 */
public class DefensiveNullException extends RuntimeException {
    private static final String NULL_MESSAGE = "Assumed controller argument is null";

    public DefensiveNullException(){
        super(NULL_MESSAGE);
    }
}
