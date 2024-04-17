package com.diplom.routeoptimizer.services.geocoding;

import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.model.UniversalAddress;

import java.io.IOException;

public interface GeocodingRequester {
    Location getLocationFromAddress(UniversalAddress address)
            throws IOException, InterruptedException;
}
