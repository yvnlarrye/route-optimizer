package com.diplom.routeoptimizer.services.routerequester;

import org.springframework.http.ResponseEntity;

public interface RouteDetailsRequester<T, V> {
    ResponseEntity<T> getRouteDetails(V requestBody);
}
