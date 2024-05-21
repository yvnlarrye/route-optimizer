package com.diplom.routeoptimizer.services;

import com.diplom.routeoptimizer.dto.RouteDetailsRequest;
import com.diplom.routeoptimizer.dto.RouteDetailsResponse;
import com.diplom.routeoptimizer.dto.vrp.VrpSolution;
import com.diplom.routeoptimizer.exceptions.EncodingAddressException;
import com.diplom.routeoptimizer.exceptions.InvalidNumberOfAddressesException;
import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.model.UniversalAddress;
import com.diplom.routeoptimizer.services.geocoding.GeocodingRequester;
import com.diplom.routeoptimizer.services.matrixparser.MatrixParser;
import com.diplom.routeoptimizer.services.ortools.VrpCapacity;
import com.diplom.routeoptimizer.services.routerequester.gis2.GIS2Requester;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RouteOptimizerServiceImpl implements RouteOptimizerService {

    private final GeocodingRequester geocodingRequester;
    private final GIS2Requester gis2Requester;
    private final MatrixParser<RouteDetailsResponse> matrixParser;

    private static final int MIN_NUMBER_OF_ADDRESSES = 3;
    private static final int GEOCODING_REQUESTS_INTERVAL = 1050;

    public ResponseEntity<RouteDetailsResponse> routeDetails(RouteDetailsRequest body) {
        return gis2Requester.getRouteDetails(body);
    }

    public ResponseEntity<?> optimize(RouteDetailsRequest request) {
        ResponseEntity<RouteDetailsResponse> responseEntity = routeDetails(request);
        Long[][] distanceMatrix = matrixParser.getDistanceMatrix(responseEntity);

        int vehicleNumber = 4;
        long[] vehicleCapacities = {3, 3, 3, 3};
        int[] demands = {0, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int depot = 0;
        VrpSolution solution = VrpCapacity.solveProblem(
                distanceMatrix, vehicleNumber, demands, vehicleCapacities, depot);

        return ResponseEntity.ok(solution);
    }

    public List<Location> getRouteLocations(List<UniversalAddress> addresses)
            throws EncodingAddressException, InvalidNumberOfAddressesException {

        if (addresses.size() < MIN_NUMBER_OF_ADDRESSES) {
            throw new InvalidNumberOfAddressesException(MIN_NUMBER_OF_ADDRESSES, addresses.size());
        }

        List<Location> locations = new ArrayList<>(MIN_NUMBER_OF_ADDRESSES);

        for (UniversalAddress address : addresses) {
            try {
                locations.add(geocodingRequester.getLocationFromAddress(address));
                Thread.sleep(GEOCODING_REQUESTS_INTERVAL);
            } catch (IOException e) {
                throw new EncodingAddressException(address, e);
            } catch (InterruptedException e) {
                throw new RuntimeException("Encoding addresses process was interrupted", e);
            }
        }
        return locations;
    }
}
