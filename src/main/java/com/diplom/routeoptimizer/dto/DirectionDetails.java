package com.diplom.routeoptimizer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DirectionDetails {
    private Long distance;
    private Long duration;
    private Long reliability;

    @JsonProperty("source_id")
    private int sourceId;

    private String status;

    @JsonProperty("target_id")
    private int targetId;
}
