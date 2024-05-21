package com.diplom.routeoptimizer.dto.vrp;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Route {
    private long distance;

    private final List<Node> nodes = new ArrayList<>();

}
