package com.diplom.routeoptimizer.services.matrix.parser;

import org.springframework.http.ResponseEntity;

public interface MatrixParser<T> {
    Long[][] parseWeights(ResponseEntity<T> matricesResponse, WeightMatrixType type);
}
