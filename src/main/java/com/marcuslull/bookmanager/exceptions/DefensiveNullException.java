package com.marcuslull.bookmanager.exceptions;

public class DefensiveNullException extends RuntimeException {
    public DefensiveNullException(){
        super("Assumed controller argument is null");
    }
}
