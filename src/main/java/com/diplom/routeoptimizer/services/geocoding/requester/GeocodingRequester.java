package com.diplom.routeoptimizer.services.geocoding.requester;

import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.services.geocoding.address.Addressable;

import java.io.IOException;

public interface GeocodingRequester {
    Location getLocationFromAddress(Addressable address)
            throws IOException, InterruptedException;
}
