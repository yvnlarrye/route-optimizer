package com.diplom.routeoptimizer.requests;

import java.io.IOException;

public class IncorrectUrlException extends IOException {
    public IncorrectUrlException(Throwable cause) {
        super(cause);
    }
}
