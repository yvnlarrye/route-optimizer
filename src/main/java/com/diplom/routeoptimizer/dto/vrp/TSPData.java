package com.diplom.routeoptimizer.dto.vrp;

import com.diplom.routeoptimizer.services.geocode.address.impl.OneStringAddress;
import lombok.Data;

import java.util.List;

@Data
public class TSPData implements RoutingProblemData {
    private List<OneStringAddress> addresses;
    private int depot;
}
