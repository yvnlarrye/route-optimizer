package com.diplom.routeoptimizer.dto;

import com.diplom.routeoptimizer.model.MapPoint;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class OptimizedRoute {
    private final List<MapPoint> optimizedRoute;
}
