package com.diplom.routeoptimizer.services.graphhopper;

import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.model.MatrixType;

import java.io.IOException;
import java.util.List;

public interface GraphHopperRequester {
    String getMatrices(List<Location> points, List<MatrixType> matrixTypes)
            throws IOException, InterruptedException;
}
