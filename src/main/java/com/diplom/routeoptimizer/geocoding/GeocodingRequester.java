package com.diplom.routeoptimizer.geocoding;

import com.diplom.routeoptimizer.model.UniversalAddress;
import okhttp3.RequestBody;

import java.io.IOException;

public interface GeocodingRequester {
    String encodeAddressToCoordinates(Addressable address) throws IOException, InterruptedException;
}
