package com.diplom.routeoptimizer.dto.vrp;

import com.diplom.routeoptimizer.services.geocode.address.Addressable;
import com.diplom.routeoptimizer.services.matrix.parser.WeightMatrixType;

import java.util.List;

public interface RoutingProblemData {
    List<? extends Addressable> getAddresses();
    WeightMatrixType getMatrixType();
}
