package com.diplom.routeoptimizer.controllers;

import com.diplom.routeoptimizer.services.geocoding.Addressable;
import com.diplom.routeoptimizer.services.geocoding.GeocodingRequester;
import com.diplom.routeoptimizer.services.graphhopper.GraphHopperRequester;
import com.diplom.routeoptimizer.services.graphhopper.MatrixParser;
import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.model.MatrixType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GeocodingController {

    private final GeocodingRequester geocodingRequester;
    private final GraphHopperRequester graphHopperRequester;

    @PostMapping("/optimize")
    public String optimize(@RequestBody List<Location> points) {
        try {
            String matricesResponse = graphHopperRequester.getMatrices(points, List.of(MatrixType.WEIGHTS));
            Object[][] matrix = MatrixParser.parseMatrix(matricesResponse, MatrixType.WEIGHTS);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < matrix.length; i++) {
                sb.append("[");
                for (int j = 0; j < matrix[i].length; j++) {
                    sb.append(matrix[i][j]);
                    if (j != matrix[i].length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]\n");
            }
            return sb.toString();
        } catch (IOException | InterruptedException e) {
            return "error";
        }
    }

    @PostMapping("/optimize")
    public String geocoding(@RequestBody List<Addressable> addresses) {
        return "";
    }

    @GetMapping("/matrix")
    public String matrix() throws IOException, InterruptedException {
        List<MatrixType> matrixTypes = new ArrayList<>();
        matrixTypes.add(MatrixType.DISTANCES);
        matrixTypes.add(MatrixType.TIMES);
        matrixTypes.add(MatrixType.WEIGHTS);

        List<Location> points = new ArrayList<>();
        points.add(new Location(-0.11379003524780275, 51.53664617804063));
        points.add(new Location(-0.10866165161132814, 51.538621486960956));
        points.add(new Location(-0.11059284210205078, 51.53245503603458));

        String matricesResponse = graphHopperRequester.getMatrices(points, matrixTypes);

        Object[][] distances = MatrixParser.parseMatrix(matricesResponse, MatrixType.WEIGHTS);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < distances.length; i++) {
            sb.append("[");
            for (int j = 0; j < distances[i].length; j++) {
                sb.append(distances[i][j]);
                if (j != distances[i].length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]\n");
        }
        return sb.toString();
    }

}

