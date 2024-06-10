package com.diplom.routeoptimizer.dto.vrp;

import com.diplom.routeoptimizer.services.optimizer.ProblemType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VrpSolution {
    private List<Route> routes = new ArrayList<>();

    @JsonProperty("problem_type")
    private ProblemType problemType;

    @JsonProperty("total_distance")
    private long totalDistance;

    @JsonProperty("total_load")
    private long totalLoad;
}
