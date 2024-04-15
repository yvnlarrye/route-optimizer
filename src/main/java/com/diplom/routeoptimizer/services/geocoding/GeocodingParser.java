package com.diplom.routeoptimizer.services.geocoding;

import com.diplom.routeoptimizer.model.Location;

public interface GeocodingParser {
    Location parse(String json);
}
