package com.diplom.routeoptimizer.geocoding;

import com.diplom.routeoptimizer.model.MapPoint;

public interface GeocodingParser {
    MapPoint parse(String json);
}
