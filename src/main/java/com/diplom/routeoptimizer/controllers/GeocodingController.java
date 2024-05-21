package com.diplom.routeoptimizer.controllers;

import com.diplom.routeoptimizer.dto.AddressesDTO;
import com.diplom.routeoptimizer.dto.RouteDetailsRequest;
import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.services.RouteOptimizerService;
import com.diplom.routeoptimizer.services.geocoding.GeocodingRequester;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GeocodingController {

    private final GeocodingRequester geocodingRequester;
    private final RouteOptimizerService routeOptimizerService;

    @ResponseBody
    @PostMapping("/optimize")
    public ResponseEntity<?> geocoding(@RequestBody AddressesDTO addressesDTO)
            throws IOException, InterruptedException {
        List<Location> routeLocations = routeOptimizerService
                            .getRouteLocations(addressesDTO.getAddresses());

        RouteDetailsRequest request = new RouteDetailsRequest(routeLocations);

        return routeOptimizerService.optimize(request);
    }

}

