package com.diplom.routeoptimizer.dto.vrp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CVRPData extends TSPData {

    private List<Integer> demands;

    @JsonProperty("vehicle_capacities")
    private List<Long> vehicleCapacities;

    @JsonProperty("vehicle_number")
    private int vehicleNumber;

}
