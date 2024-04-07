package com.diplom.routeoptimizer.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MapPoint {
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return String.format("lat: %f, lon: %f", latitude, longitude);
    }
}
