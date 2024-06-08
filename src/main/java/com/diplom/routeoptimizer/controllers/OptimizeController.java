package com.diplom.routeoptimizer.controllers;

import com.diplom.routeoptimizer.dto.vrp.CVRPData;
import com.diplom.routeoptimizer.dto.vrp.TSPData;
import com.diplom.routeoptimizer.services.optimizer.OptimalRouteFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/find")
@RequiredArgsConstructor
public class OptimizeController {

    private final OptimalRouteFinder optimalRouteFinder;

    @PostMapping("/cvrp")
    public ResponseEntity<?> optimizeCVRP(@RequestBody CVRPData cvrp) throws IOException {
        return optimalRouteFinder.findWithCVRP(cvrp);
    }

    @PostMapping("/tsp")
    public ResponseEntity<?> optimizeTSP(@RequestBody TSPData tsp) throws IOException {
        return optimalRouteFinder.findWithTSP(tsp);
    }
}

