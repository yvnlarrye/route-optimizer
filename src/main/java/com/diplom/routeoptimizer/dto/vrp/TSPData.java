package com.diplom.routeoptimizer.dto.vrp;

import com.diplom.routeoptimizer.services.geocode.address.impl.OneStringAddress;
import com.diplom.routeoptimizer.services.matrix.parser.WeightMatrixType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TSPData implements RoutingProblemData {
    private List<OneStringAddress> addresses;
    @JsonProperty("optimization_type")
    private WeightMatrixType matrixType;
    private int depot;
}
