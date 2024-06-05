package com.diplom.routeoptimizer.services.geocoding.parser;

import com.diplom.routeoptimizer.model.Location;

public interface LocationParser {
    Location parse(String json);
}
