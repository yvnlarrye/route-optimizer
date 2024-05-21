package com.diplom.routeoptimizer.dto.vrp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VrpSolution {
    @JsonProperty("total_distance")
    private long totalDistance;

    @JsonProperty("total_load")
    private long totalLoad;
    private final List<Route> routes = new ArrayList<>();
}
