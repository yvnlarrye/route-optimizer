package com.diplom.routeoptimizer.services.geocode.geocoder;

import com.diplom.routeoptimizer.services.geocode.Location;
import com.diplom.routeoptimizer.services.geocode.address.Addressable;

import java.io.IOException;

public interface Geocoder {
    Location geocode(Addressable address)
            throws IOException, InterruptedException;
}
