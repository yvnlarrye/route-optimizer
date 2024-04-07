package com.diplom.routeoptimizer;

import com.diplom.routeoptimizer.geocoding.GeocodingRequester;
import com.diplom.routeoptimizer.graphhopper.GraphHopperRequester;
import com.diplom.routeoptimizer.model.UniversalAddress;
import com.diplom.routeoptimizer.model.MapPoint;
import com.diplom.routeoptimizer.model.MatrixType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GeocodingController {

    private GeocodingRequester geocodingRequester;
//    private GraphHopperRequester graphHopperRequester;

    @Autowired
    public GeocodingController(GeocodingRequester geocodingRequester) {
        this.geocodingRequester = geocodingRequester;
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

//    @GetMapping("/matrix")
//    public JSONObject matrix() throws IOException {
//        List<MatrixType> matrixTypes = new ArrayList<>();
//        matrixTypes.add(MatrixType.DISTANCES);
//        matrixTypes.add(MatrixType.TIMES);
//
//        List<MapPoint> points = new ArrayList<>();
//        points.add(new MapPoint(30.329864, 59.861024));
//        points.add(new MapPoint(30.329864, 59.861024));
//        points.add(new MapPoint(30.329864, 59.861024));
//
//        return graphHopperRequester.getMatrices(points, matrixTypes);
//    }

}

