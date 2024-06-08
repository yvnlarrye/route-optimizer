package com.diplom.routeoptimizer.dto.vrp;

import com.diplom.routeoptimizer.services.geocode.address.Addressable;

import java.util.List;

public interface RoutingProblemData {
    List<? extends Addressable> getAddresses();
}
