package com.diplom.routeoptimizer.services.geocode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class Location {
    private double lat;
    private double lon;

    @Override
    public String toString() {
        return String.format("lat: %f, lon: %f", lat, lon);
    }
}
