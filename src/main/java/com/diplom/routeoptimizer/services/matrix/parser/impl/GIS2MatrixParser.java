package com.diplom.routeoptimizer.services.matrix.parser.impl;

import com.diplom.routeoptimizer.dto.DirectionDetails;
import com.diplom.routeoptimizer.dto.RouteDetailsResponse;
import com.diplom.routeoptimizer.services.matrix.parser.MatrixParser;
import com.diplom.routeoptimizer.services.matrix.parser.WeightMatrixType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GIS2MatrixParser implements MatrixParser<RouteDetailsResponse> {

    @Override
    public Long[][] parseWeights(ResponseEntity<RouteDetailsResponse> matricesResponse, WeightMatrixType type) {
        RouteDetailsResponse body = matricesResponse.getBody();
        if (body == null) {
            return null;
        }
        List<DirectionDetails> routes = body.getRoutes();
        int dimension = (int) java.lang.Math.sqrt(routes.size());
        Long[][] weights = new Long[dimension][dimension];
        routes.forEach(route ->
                weights[route.getSourceId()][route.getTargetId()] = switch (type) {
                    case DISTANCES -> route.getDistance();
                    case DURATIONS -> route.getDuration();
                });
        return weights;
    }
}
