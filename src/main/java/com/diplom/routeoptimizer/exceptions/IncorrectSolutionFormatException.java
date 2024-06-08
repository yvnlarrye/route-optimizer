package com.diplom.routeoptimizer.exceptions;

public class IncorrectSolutionFormatException extends RuntimeException {
    public IncorrectSolutionFormatException(String message) {
        super(message);
    }

    public IncorrectSolutionFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
