package com.diplom.routeoptimizer.services;

import com.diplom.routeoptimizer.dto.RouteDetailsRequest;
import com.diplom.routeoptimizer.dto.RouteDetailsResponse;
import com.diplom.routeoptimizer.exceptions.EncodingAddressException;
import com.diplom.routeoptimizer.exceptions.InvalidNumberOfAddressesException;
import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.model.UniversalAddress;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RouteOptimizerService {
    List<Location> getRouteLocations(List<UniversalAddress> addresses)
            throws EncodingAddressException, InvalidNumberOfAddressesException;

    ResponseEntity<RouteDetailsResponse> routeDetails(RouteDetailsRequest body);
    ResponseEntity<?> optimize(RouteDetailsRequest request);
}