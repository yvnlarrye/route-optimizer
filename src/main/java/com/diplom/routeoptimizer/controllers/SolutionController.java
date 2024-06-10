package com.diplom.routeoptimizer.controllers;

import com.diplom.routeoptimizer.dto.vrp.SolutionDTO;
import com.diplom.routeoptimizer.dto.vrp.VrpSolution;
import com.diplom.routeoptimizer.model.Solution;
import com.diplom.routeoptimizer.services.SolutionService;
import com.diplom.routeoptimizer.services.reportbuilder.ReportFileBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/solution")
@RequiredArgsConstructor
public class SolutionController {

    private final SolutionService solutionService;
    private final ReportFileBuilder reportFileBuilder;

    @PostMapping
    public ResponseEntity<?> saveSolution(@RequestBody SolutionDTO solution) {
        solutionService.create(solution);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Solution saved successfully"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Solution>> getAllUserSolutions(@PathVariable Long userId) {
        return ResponseEntity.ok(solutionService.getUserSolutions(userId));
    }

    @PostMapping("/download")
    public ResponseEntity<Resource> downloadSolution(@RequestBody SolutionDTO solutionDTO) {

        VrpSolution solution = solutionService.readSolutionFromDTO(solutionDTO);
        byte[] reportFile = reportFileBuilder.createReportFile(solution);

        Resource fileResource = new ByteArrayResource(reportFile);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"solution.xlsx\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(reportFile.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileResource);
    }
}
