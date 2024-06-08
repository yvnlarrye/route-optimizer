package com.diplom.routeoptimizer.services.geocode.parser;

import com.diplom.routeoptimizer.model.Location;

public interface LocationParser {
    Location parse(String json);
}
