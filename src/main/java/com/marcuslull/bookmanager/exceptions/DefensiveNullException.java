package com.marcuslull.bookmanager.exceptions;

public class DefensiveNullException extends RuntimeException {
    private static final String NULL_MESSAGE = "Assumed controller argument is null";

    public DefensiveNullException(){
        super(NULL_MESSAGE);
    }
}
