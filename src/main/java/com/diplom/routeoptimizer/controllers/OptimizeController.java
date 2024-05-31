package com.diplom.routeoptimizer.controllers;

import com.diplom.routeoptimizer.dto.vrp.CvrpRequest;
import com.diplom.routeoptimizer.dto.vrp.TspRequest;
import com.diplom.routeoptimizer.services.RouteOptimizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/optimize")
@RequiredArgsConstructor
public class OptimizeController {

    private final RouteOptimizerService routeOptimizerService;

    @PostMapping("/cvrp")
    public ResponseEntity<?> optimizeCVRP(@RequestBody CvrpRequest cvrpRequest) throws IOException {
        return routeOptimizerService.optimizeCvrp(cvrpRequest);
    }

    @PostMapping("/tsp")
    public ResponseEntity<?> optimizeTSP(@RequestBody TspRequest tspRequest) throws IOException {
        return routeOptimizerService.optimizeTsp(tspRequest);
    }
}

