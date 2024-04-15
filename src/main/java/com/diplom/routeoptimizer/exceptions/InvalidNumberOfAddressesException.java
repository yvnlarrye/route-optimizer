package com.diplom.routeoptimizer.exceptions;

import java.io.IOException;

public class InvalidNumberOfAddressesException extends IOException {

    private static final String message = "Number of addresses must be greater than or equal to %d, but received %d";

    public InvalidNumberOfAddressesException(int maxNumberOfAddresses, int actualNumberOfAddresses) {
        super(String.format(message, maxNumberOfAddresses, actualNumberOfAddresses));
    }
}
