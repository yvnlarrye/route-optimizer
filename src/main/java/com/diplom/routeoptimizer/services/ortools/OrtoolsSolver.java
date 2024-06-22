package com.diplom.routeoptimizer.services.ortools;

import com.diplom.routeoptimizer.dto.vrp.Node;
import com.diplom.routeoptimizer.dto.vrp.Route;
import com.diplom.routeoptimizer.dto.vrp.VrpSolution;
import com.diplom.routeoptimizer.exceptions.SolutionNotFoundException;
import com.diplom.routeoptimizer.services.optimizer.DataModel;
import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import com.google.protobuf.Duration;

import java.util.Arrays;

public final class OrtoolsSolver {

    private static VrpSolution formatSolution(
            DataModel data, RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
        long totalDistance = 0;
        long totalLoad = 0;
        VrpSolution vrpSolution = new VrpSolution();

        for (int i = 0; i < data.getVehicleNumber(); ++i) {
            Route route = new Route();
            long index = routing.start(i);
            long routeDistance = 0;
            int routeLoad = Arrays.stream(data.getDemands()).sum();

            while (!routing.isEnd(index)) {
                int nodeIndex = manager.indexToNode(index);
                routeLoad -= data.getDemands()[nodeIndex];

                Node node = Node.builder()
                        .id(nodeIndex)
                        .demand(data.getDemands()[nodeIndex])
                        .supplied(data.getDemands()[nodeIndex])
                        .load(routeLoad)
                        .address(data.getAddresses().get(nodeIndex))
                        .location(data.getLocations().get(nodeIndex))
                        .build();

                route.getNodes().add(node);
                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i);
            }

            Node depot = Node.builder()
                    .id(manager.indexToNode(routing.end(i)))
                    .load(0)
                    .address(data.getAddresses().get(manager.indexToNode(routing.end(i))))
                    .location(data.getLocations().get(manager.indexToNode(routing.end(i))))
                    .build();

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

    public static VrpSolution solveProblem(DataModel data) {

        Loader.loadNativeLibraries();

        RoutingIndexManager manager =
                new RoutingIndexManager(data.getWeightMatrix().length, data.getVehicleNumber(), data.getDepot());

        RoutingModel routing = new RoutingModel(manager);
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return data.getWeightMatrix()[fromNode][toNode];
                });

        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        final int demandCallbackIndex = routing.registerUnaryTransitCallback((long fromIndex) -> {
            int fromNode = manager.indexToNode(fromIndex);
            return data.getDemands()[fromNode];
        });
        routing.addDimensionWithVehicleCapacity(demandCallbackIndex, 0,
                data.getVehicleCapacities(),
                true,
                "Capacity");

        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .setLocalSearchMetaheuristic(LocalSearchMetaheuristic.Value.GUIDED_LOCAL_SEARCH)
                        .setTimeLimit(Duration.newBuilder().setSeconds(10).build())
                        .build();

        Assignment solution = routing.solveWithParameters(searchParameters);

        if (solution != null) {
            return formatSolution(data, routing, manager, solution);
        }

        throw new SolutionNotFoundException("Failed to find solution with provided input");
    }

    private OrtoolsSolver() {
    }
}