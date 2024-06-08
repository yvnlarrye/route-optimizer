package com.diplom.routeoptimizer.controllers;

import com.diplom.routeoptimizer.dto.vrp.SolutionDTO;
import com.diplom.routeoptimizer.services.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/solution")
@RequiredArgsConstructor
public class SolutionController {

    private final SolutionService solutionService;

    @PostMapping
    public ResponseEntity<?> saveSolution(@RequestBody SolutionDTO solution) {
        solutionService.create(solution);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Solution saved successfully"));
    }

}
