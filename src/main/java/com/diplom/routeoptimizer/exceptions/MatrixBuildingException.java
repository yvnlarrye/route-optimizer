package com.diplom.routeoptimizer.exceptions;

import java.io.IOException;

public class MatrixBuildingException extends IOException {

    private static final String message = "Error occurred while requesting matrix";

    public MatrixBuildingException(Throwable cause) {
        super(message, cause);
    }
}
