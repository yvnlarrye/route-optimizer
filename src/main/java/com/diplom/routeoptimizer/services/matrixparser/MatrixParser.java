package com.diplom.routeoptimizer.services.matrixparser;

import org.springframework.http.ResponseEntity;

public interface MatrixParser<T> {
    Long[][] getDurationMatrix(ResponseEntity<T> matricesResponse);
    Long[][] getDistanceMatrix(ResponseEntity<T> matricesResponse);
}
