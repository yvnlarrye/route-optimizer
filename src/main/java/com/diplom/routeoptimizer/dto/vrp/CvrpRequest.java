package com.diplom.routeoptimizer.dto.vrp;

import com.diplom.routeoptimizer.model.UniversalAddress;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CvrpRequest {
    private List<UniversalAddress> addresses;
    private List<Integer> demands;

    @JsonProperty("vehicle_capacities")
    private List<Long> vehicleCapacities;

    @JsonProperty("vehicle_number")
    private int vehicleNumber;

    private int depot;
}
