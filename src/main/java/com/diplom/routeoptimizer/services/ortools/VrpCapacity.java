package com.diplom.routeoptimizer.services.ortools;

import com.diplom.routeoptimizer.dto.vrp.Node;
import com.diplom.routeoptimizer.dto.vrp.Route;
import com.diplom.routeoptimizer.dto.vrp.VrpSolution;
import com.diplom.routeoptimizer.exceptions.SolutionNotFoundException;
import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import com.google.protobuf.Duration;
import lombok.Builder;
import lombok.Data;

public final class VrpCapacity {

    @Builder
    @Data
    static class DataModel {
        private Long[][] distanceMatrix;

        public int[] demands;
        public long[] vehicleCapacities;
        public int vehicleNumber;
        public int depot;

    }

    private static VrpSolution formatSolution(DataModel data, RoutingModel routing,
                                              RoutingIndexManager manager, Assignment solution) {
        long totalDistance = 0;
        long totalLoad = 0;
        VrpSolution vrpSolution = new VrpSolution();
        for (int i = 0; i < data.vehicleNumber; ++i) {
            Route route = new Route();
            long index = routing.start(i);
            long routeDistance = 0;
            int routeLoad = 0;

            while (!routing.isEnd(index)) {
                int nodeIndex = manager.indexToNode(index);
                routeLoad += data.demands[nodeIndex];
                Node node = new Node(nodeIndex, routeLoad);
                route.getNodes().add(node);
                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i);
            }
            Node depot = new Node(manager.indexToNode(routing.end(i)), 0);
            route.getNodes().add(depot);
            route.setDistance(routeDistance);
            totalDistance += routeDistance;
            totalLoad += routeLoad;

            vrpSolution.getRoutes().add(route);
        }
        vrpSolution.setTotalDistance(totalDistance);
        vrpSolution.setTotalLoad(totalLoad);
        return vrpSolution;
    }

    public static VrpSolution solveProblem(Long[][] distanceMatrix, int vehicleNumber,
                                           int[] demands, long[] vehicleCapacities, int depot) {
        Loader.loadNativeLibraries();

        final DataModel data = DataModel.builder()
                .distanceMatrix(distanceMatrix)
                .vehicleNumber(vehicleNumber)
                .demands(demands)
                .vehicleCapacities(vehicleCapacities)
                .depot(depot)
                .build();

        RoutingIndexManager manager =
                new RoutingIndexManager(data.distanceMatrix.length, data.vehicleNumber, data.depot);

        RoutingModel routing = new RoutingModel(manager);
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return data.distanceMatrix[fromNode][toNode];
                });

        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        final int demandCallbackIndex = routing.registerUnaryTransitCallback((long fromIndex) -> {
            int fromNode = manager.indexToNode(fromIndex);
            return data.demands[fromNode];
        });
        routing.addDimensionWithVehicleCapacity(demandCallbackIndex, 0,
                data.vehicleCapacities,
                true,
                "Capacity");

        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .setLocalSearchMetaheuristic(LocalSearchMetaheuristic.Value.GUIDED_LOCAL_SEARCH)
                        .setTimeLimit(Duration.newBuilder().setSeconds(3).build())
                        .build();

        Assignment solution = routing.solveWithParameters(searchParameters);

        if (solution != null) {
            return formatSolution(data, routing, manager, solution);
        }

        throw new SolutionNotFoundException("Failed to find solution with provided input");
    }

    private VrpCapacity() {
    }
}