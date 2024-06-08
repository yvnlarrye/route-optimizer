package com.diplom.routeoptimizer.exceptions;

import com.diplom.routeoptimizer.services.geocode.address.Addressable;

import java.io.IOException;

public class EncodingAddressException extends RuntimeException {

    private static final String message = "Something went wrong while trying encode address: ";

    public EncodingAddressException(Throwable cause) {
        super(cause);
    }
    public EncodingAddressException(Addressable address, Throwable cause) {
        super(message + address.toString(), cause);
    }
}
