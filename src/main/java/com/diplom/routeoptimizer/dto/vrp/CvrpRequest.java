package com.diplom.routeoptimizer.dto.vrp;

import com.diplom.routeoptimizer.services.geocoding.address.impl.OneStringAddress;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CvrpRequest {
    private List<OneStringAddress> addresses;
    private List<Integer> demands;

    @JsonProperty("vehicle_capacities")
    private List<Long> vehicleCapacities;

    @JsonProperty("vehicle_number")
    private int vehicleNumber;

    private int depot;
}
