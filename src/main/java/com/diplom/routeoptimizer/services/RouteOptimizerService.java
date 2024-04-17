package com.diplom.routeoptimizer.services;

import com.diplom.routeoptimizer.exceptions.EncodingAddressException;
import com.diplom.routeoptimizer.exceptions.InvalidNumberOfAddressesException;
import com.diplom.routeoptimizer.exceptions.MatrixBuildingException;
import com.diplom.routeoptimizer.model.MapPoint;
import com.diplom.routeoptimizer.model.UniversalAddress;

import java.util.List;

public interface RouteOptimizerService {
    List<MapPoint> optimizeRoute(List<UniversalAddress> addresses)
            throws InvalidNumberOfAddressesException, EncodingAddressException, MatrixBuildingException;
}