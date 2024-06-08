package com.diplom.routeoptimizer.services.optimizer;

import com.diplom.routeoptimizer.dto.vrp.CVRPData;
import com.diplom.routeoptimizer.dto.vrp.TSPData;
import com.diplom.routeoptimizer.exceptions.EncodingAddressException;
import com.diplom.routeoptimizer.exceptions.InvalidNumberOfAddressesException;
import org.springframework.http.ResponseEntity;

public interface OptimalRouteFinder {
    ResponseEntity<?> findWithCVRP(CVRPData CVRPDefinition)
            throws InvalidNumberOfAddressesException, EncodingAddressException;

    ResponseEntity<?> findWithTSP(TSPData request) throws InvalidNumberOfAddressesException, EncodingAddressException;
}