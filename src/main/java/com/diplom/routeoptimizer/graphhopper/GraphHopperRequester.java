package com.diplom.routeoptimizer.graphhopper;

import com.diplom.routeoptimizer.model.MapPoint;
import com.diplom.routeoptimizer.model.MatrixType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GraphHopperRequester {
    Map<MatrixType, double[][]> getMatrices(List<MapPoint> points, List<MatrixType> matrixTypes) throws IOException;

}
