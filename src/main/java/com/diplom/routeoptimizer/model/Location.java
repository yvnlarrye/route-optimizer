package com.diplom.routeoptimizer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Location {
    private double latitude;
    private double longitude;

    @Override
    public String toString() {
        return String.format("lat: %f, lon: %f", latitude, longitude);
    }
}
