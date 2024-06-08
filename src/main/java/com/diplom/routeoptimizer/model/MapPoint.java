package com.diplom.routeoptimizer.model;

import com.diplom.routeoptimizer.services.geocode.address.Addressable;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class MapPoint {
    private final Location location;
    private final Addressable address;
}

