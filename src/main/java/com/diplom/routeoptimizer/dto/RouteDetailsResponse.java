package com.diplom.routeoptimizer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RouteDetailsResponse {

    @JsonProperty("generation_time")
    private Long generationTime;

    private List<DirectionDetails> routes;

}
