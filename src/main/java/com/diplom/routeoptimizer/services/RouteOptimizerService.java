package com.diplom.routeoptimizer.services;

import com.diplom.routeoptimizer.dto.vrp.CvrpRequest;
import com.diplom.routeoptimizer.dto.vrp.TspRequest;
import com.diplom.routeoptimizer.exceptions.EncodingAddressException;
import com.diplom.routeoptimizer.exceptions.InvalidNumberOfAddressesException;
import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.services.geocoding.address.Addressable;
import com.diplom.routeoptimizer.services.geocoding.address.impl.OneStringAddress;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RouteOptimizerService {
    List<Location> getRouteLocations(List<? extends Addressable> oneStringAddresses)
            throws EncodingAddressException, InvalidNumberOfAddressesException;

    ResponseEntity<?> optimizeCvrp(CvrpRequest cvrpRequest)
            throws InvalidNumberOfAddressesException, EncodingAddressException;

    ResponseEntity<?> optimizeTsp(TspRequest request) throws InvalidNumberOfAddressesException, EncodingAddressException;
}