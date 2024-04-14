package com.diplom.routeoptimizer;

import com.diplom.routeoptimizer.geocoding.GeocodingRequester;
import com.diplom.routeoptimizer.graphhopper.GraphHopperRequester;
import com.diplom.routeoptimizer.graphhopper.MatrixParser;
import com.diplom.routeoptimizer.model.UniversalAddress;
import com.diplom.routeoptimizer.model.MapPoint;
import com.diplom.routeoptimizer.model.MatrixType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<> optimize(@RequestBody List<MapPoint> points) {
        try {
            String matricesResponse = graphHopperRequester.getMatrices(points, List.of(MatrixType.WEIGHTS));
        } catch (IOException | InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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

    @GetMapping("/geocoding")
    public String geocoding() {
        UniversalAddress address = new UniversalAddress("Russia", "Санкт-Петербург",
                "Тамбасова", "8к1");
        try {
            return geocodingRequester.encodeAddressToCoordinates(address);
        } catch (Exception e) {
            return "Ошибка" + e.getMessage();
        }
    }

    @GetMapping("/matrix")
    public String matrix() throws IOException, InterruptedException {
        List<MatrixType> matrixTypes = new ArrayList<>();
        matrixTypes.add(MatrixType.DISTANCES);
        matrixTypes.add(MatrixType.TIMES);
        matrixTypes.add(MatrixType.WEIGHTS);

        List<MapPoint> points = new ArrayList<>();
        points.add(new MapPoint(-0.11379003524780275, 51.53664617804063));
        points.add(new MapPoint(-0.10866165161132814, 51.538621486960956));
        points.add(new MapPoint(-0.11059284210205078, 51.53245503603458));

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

