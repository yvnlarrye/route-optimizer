package com.diplom.routeoptimizer.dto;

import com.diplom.routeoptimizer.model.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RouteDetailsRequest {
    private List<Location> points;
    private List<Integer> sources;
    private List<Integer> targets;


    public RouteDetailsRequest(List<Location> points) {
        this.points = points;
        this.sources = new ArrayList<>(points.size());
        this.targets = new ArrayList<>(points.size());
        for (int i = 0; i < points.size(); i++) {
            sources.add(i);
            targets.add(i);
        }
    }
}
