package com.diplom.routeoptimizer.services.optimizer.impl;

import com.diplom.routeoptimizer.dto.RouteDetailsRequest;
import com.diplom.routeoptimizer.dto.RouteDetailsResponse;
import com.diplom.routeoptimizer.dto.vrp.CVRPData;
import com.diplom.routeoptimizer.dto.vrp.RoutingProblemData;
import com.diplom.routeoptimizer.dto.vrp.TSPData;
import com.diplom.routeoptimizer.exceptions.EncodingAddressException;
import com.diplom.routeoptimizer.exceptions.InvalidNumberOfAddressesException;
import com.diplom.routeoptimizer.services.geocode.Location;
import com.diplom.routeoptimizer.services.geocode.address.Addressable;
import com.diplom.routeoptimizer.services.geocode.geocoder.Geocoder;
import com.diplom.routeoptimizer.services.matrix.parser.MatrixParser;
import com.diplom.routeoptimizer.services.optimizer.DataModel;
import com.diplom.routeoptimizer.services.optimizer.OptimalRouteFinder;
import com.diplom.routeoptimizer.services.ortools.OrtoolsSolver;
import com.diplom.routeoptimizer.services.route.builder.gis2.GIS2RouteBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class OptimalRouteFinderImpl implements OptimalRouteFinder {

    private final Geocoder geocoder;
    private final GIS2RouteBuilder gis2RouteFinder;
    private final MatrixParser<RouteDetailsResponse> matrixParser;

    private static final int MIN_NUMBER_OF_ADDRESSES = 3;
    private static final int GEOCODING_REQUESTS_INTERVAL = 0;

    @Override
    public ResponseEntity<?> findWithCVRP(CVRPData cvrp)
            throws InvalidNumberOfAddressesException, EncodingAddressException {

        DataModel dataModel = initDataModel(cvrp)
                .demands(cvrp.getDemands().stream().mapToInt(Integer::intValue).toArray())
                .vehicleNumber(cvrp.getVehicleNumber())
                .vehicleCapacities(cvrp.getVehicleCapacities().stream().mapToLong(Long::longValue).toArray())
                .depot(cvrp.getDepot())
                .build();

        return ResponseEntity.ok(OrtoolsSolver.solveProblem(dataModel));
    }

    @Override
    public ResponseEntity<?> findWithTSP(TSPData tsp)
            throws InvalidNumberOfAddressesException, EncodingAddressException {

        DataModel dataModel = initDataModel(tsp)
                .demands(IntStream.generate(() -> 1).limit(tsp.getAddresses().size()).toArray())
                .vehicleNumber(1)
                .vehicleCapacities(new long[]{tsp.getAddresses().size()})
                .depot(tsp.getDepot())
                .build();

        return ResponseEntity.ok(OrtoolsSolver.solveProblem(dataModel));
    }

    private DataModel.DataModelBuilder initDataModel(RoutingProblemData problem) throws InvalidNumberOfAddressesException {
        List<Location> routeLocations = getRouteLocations(problem.getAddresses());
        ResponseEntity<RouteDetailsResponse> response = routeDetails(new RouteDetailsRequest(routeLocations));
        Long[][] weightsMatrix = matrixParser.parseWeights(response, problem.getMatrixType());
        return DataModel.builder()
                .addresses(problem.getAddresses())
                .locations(routeLocations)
                .weightMatrix(weightsMatrix);
    }

    private ResponseEntity<RouteDetailsResponse> routeDetails(RouteDetailsRequest body) {
        return gis2RouteFinder.getRouteDetails(body);
    }

    private Location getLocationByAddress(Addressable address) throws EncodingAddressException {
        try {
            Location location = geocoder.geocode(address);
            Thread.sleep(GEOCODING_REQUESTS_INTERVAL);
            return location;
        } catch (IOException e) {
            throw new EncodingAddressException(address, e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Encoding addresses process was interrupted", e);
        }
    }

    private List<Location> getRouteLocations(List<? extends Addressable> addresses)
            throws EncodingAddressException, InvalidNumberOfAddressesException {

        if (addresses.size() < MIN_NUMBER_OF_ADDRESSES) {
            throw new InvalidNumberOfAddressesException(MIN_NUMBER_OF_ADDRESSES, addresses.size());
        }

        return addresses.stream().map(this::getLocationByAddress).toList();
    }
}
