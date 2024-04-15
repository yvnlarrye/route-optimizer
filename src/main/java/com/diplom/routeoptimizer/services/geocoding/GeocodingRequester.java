package com.diplom.routeoptimizer.services.geocoding;

import java.io.IOException;

public interface GeocodingRequester {
    String encodeAddressToCoordinates(Addressable address)
            throws IOException, InterruptedException;
}
