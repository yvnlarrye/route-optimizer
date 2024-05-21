package com.diplom.routeoptimizer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Location {
    private double lat;
    private double lon;

    @Override
    public String toString() {
        return String.format("lat: %f, lon: %f", lat, lon);
    }
}
