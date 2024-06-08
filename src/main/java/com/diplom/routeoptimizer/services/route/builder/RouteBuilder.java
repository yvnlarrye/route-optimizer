package com.diplom.routeoptimizer.services.route.builder;

import org.springframework.http.ResponseEntity;

public interface RouteBuilder<T, V> {
    ResponseEntity<T> getRouteDetails(V requestBody);
}
