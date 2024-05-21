package com.diplom.routeoptimizer.services.matrixparser.impl;

import com.diplom.routeoptimizer.dto.DirectionDetails;
import com.diplom.routeoptimizer.dto.RouteDetailsResponse;
import com.diplom.routeoptimizer.services.matrixparser.MatrixParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GIS2MatrixParser implements MatrixParser<RouteDetailsResponse> {

    @Override
    public Long[][] getDistanceMatrix(ResponseEntity<RouteDetailsResponse> matricesResponse) {
        RouteDetailsResponse body = matricesResponse.getBody();
        if (body == null) {
            return null;
        }
        List<DirectionDetails> routes = body.getRoutes();
        int dimension = (int) java.lang.Math.sqrt(routes.size());
        Long[][] distances = new Long[dimension][dimension];
        routes.forEach(route ->
                distances[route.getSourceId()][route.getTargetId()] = route.getDistance());
        return distances;
    }

    @Override
    public Long[][] getDurationMatrix(ResponseEntity<RouteDetailsResponse> matricesResponse) {
        RouteDetailsResponse body = matricesResponse.getBody();
        if (body == null) {
            return null;
        }
        List<DirectionDetails> routes = body.getRoutes();
        int dimension = (int) java.lang.Math.sqrt(routes.size());
        Long[][] durations = new Long[dimension][dimension];
        routes.forEach(route ->
                durations[route.getSourceId()][route.getTargetId()] = route.getDuration());
        return durations;
    }
}
