package com.diplom.routeoptimizer.services.optimizer;

import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.services.geocode.address.Addressable;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataModel {
    private List<? extends Addressable> addresses;
    private List<Location> locations;
    private Long[][] weightMatrix;
    private int[] demands;
    private long[] vehicleCapacities;
    private int vehicleNumber;
    private int depot;
}
