package com.diplom.routeoptimizer.services;

import com.diplom.routeoptimizer.dto.RouteDetailsRequest;
import com.diplom.routeoptimizer.dto.RouteDetailsResponse;
import com.diplom.routeoptimizer.dto.vrp.CvrpRequest;
import com.diplom.routeoptimizer.dto.vrp.TspRequest;
import com.diplom.routeoptimizer.dto.vrp.VrpSolution;
import com.diplom.routeoptimizer.exceptions.EncodingAddressException;
import com.diplom.routeoptimizer.exceptions.InvalidNumberOfAddressesException;
import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.services.geocoding.address.Addressable;
import com.diplom.routeoptimizer.services.geocoding.requester.GeocodingRequester;
import com.diplom.routeoptimizer.services.matrixparser.MatrixParser;
import com.diplom.routeoptimizer.services.ortools.VrpCapacity;
import com.diplom.routeoptimizer.services.routerequester.gis2.GIS2Requester;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RouteOptimizerServiceImpl implements RouteOptimizerService {

    private final GeocodingRequester geocodingRequester;
    private final GIS2Requester gis2Requester;
    private final MatrixParser<RouteDetailsResponse> matrixParser;

    private static final int MIN_NUMBER_OF_ADDRESSES = 3;
    private static final int GEOCODING_REQUESTS_INTERVAL = 100;

    public ResponseEntity<RouteDetailsResponse> routeDetails(RouteDetailsRequest body) {
        return gis2Requester.getRouteDetails(body);
    }

    @Override
    public ResponseEntity<?> optimizeCvrp(CvrpRequest cvrpRequest)
            throws InvalidNumberOfAddressesException, EncodingAddressException {

        List<Location> routeLocations = getRouteLocations(cvrpRequest.getAddresses());
        RouteDetailsRequest request = new RouteDetailsRequest(routeLocations);

        ResponseEntity<RouteDetailsResponse> responseEntity = routeDetails(request);
        Long[][] distanceMatrix = matrixParser.getDistanceMatrix(responseEntity);

        int vehicleNumber = cvrpRequest.getVehicleNumber();
        long[] vehicleCapacities = cvrpRequest.getVehicleCapacities().stream().mapToLong(Long::longValue).toArray();
        int[] demands = cvrpRequest.getDemands().stream().mapToInt(Integer::intValue).toArray();
        int depot = cvrpRequest.getDepot();
        VrpSolution solution = VrpCapacity.solveProblem(
                distanceMatrix, vehicleNumber, demands, vehicleCapacities, depot);

        return ResponseEntity.ok(solution);
    }

    @Override
    public ResponseEntity<?> optimizeTsp(TspRequest tspRequest)
            throws InvalidNumberOfAddressesException, EncodingAddressException {

        System.out.println(tspRequest);

        List<Location> routeLocations = getRouteLocations(tspRequest.getAddresses());

        RouteDetailsRequest request = new RouteDetailsRequest(routeLocations);
        ResponseEntity<RouteDetailsResponse> responseEntity = routeDetails(request);
        Long[][] distanceMatrix = matrixParser.getDistanceMatrix(responseEntity);

        int[] demands = IntStream.generate(() -> 1).limit(distanceMatrix.length).toArray();
        long[] vehicleCapacities = {Long.MAX_VALUE};
        VrpSolution solution = VrpCapacity.solveProblem(
                distanceMatrix, 1, demands, vehicleCapacities, 0);

        return ResponseEntity.ok(solution);
    }

    public List<Location> getRouteLocations(List<? extends Addressable> addresses)
            throws EncodingAddressException, InvalidNumberOfAddressesException {

        if (addresses.size() < MIN_NUMBER_OF_ADDRESSES) {
            throw new InvalidNumberOfAddressesException(MIN_NUMBER_OF_ADDRESSES, addresses.size());
        }

        List<Location> locations = new ArrayList<>(MIN_NUMBER_OF_ADDRESSES);

        for (Addressable address : addresses) {
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
