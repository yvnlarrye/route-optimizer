package com.diplom.routeoptimizer.services;

import com.diplom.routeoptimizer.exceptions.EncodingAddressException;
import com.diplom.routeoptimizer.exceptions.InvalidNumberOfAddressesException;
import com.diplom.routeoptimizer.exceptions.MatrixBuildingException;
import com.diplom.routeoptimizer.model.MapPoint;
import com.diplom.routeoptimizer.model.MatrixType;
import com.diplom.routeoptimizer.model.UniversalAddress;
import com.diplom.routeoptimizer.services.geocoding.GeocodingRequester;
import com.diplom.routeoptimizer.services.graphhopper.GraphHopperRequester;
import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.services.graphhopper.MatrixParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.diplom.routeoptimizer.optimizer.dijkstra.*;

@Service
@RequiredArgsConstructor
public class RouteOptimizerServiceImpl implements RouteOptimizerService {

    private final GeocodingRequester geocodingRequester;
    private final GraphHopperRequester graphHopperRequester;

    private static final int MIN_NUMBER_OF_ADDRESSES = 3;
    private static final int GEOCODING_REQUESTS_INTERVAL = 1050;
    private static final int SOURCE_NODE_INDEX = 0;

    private List<Location> getRouteLocations(List<UniversalAddress> addresses)
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

    public List<MapPoint> optimizeRoute(List<UniversalAddress> addresses)
            throws InvalidNumberOfAddressesException, EncodingAddressException, MatrixBuildingException {
        List<Location> locations = getRouteLocations(addresses);

        try {
            String matricesResponse = graphHopperRequester.getMatrices(locations, List.of(MatrixType.WEIGHTS));
            Object[][] matrix = MatrixParser.parseMatrix(matricesResponse, MatrixType.WEIGHTS);

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }

            Node[] nodes = new Node[locations.size()];

            for (int i = 0; i < matrix.length; i++) {
                MapPoint point = new MapPoint(locations.get(i), addresses.get(i));
                nodes[i] = new Node(point);
            }

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (i != j) {
                        nodes[i].addDestination(nodes[j], (BigDecimal) matrix[i][j]);
                    }
                }
            }

            Graph graph = new Graph();
            graph.addNodes(nodes);

            Dijkstra.calculateShortestPathFromSource(nodes[SOURCE_NODE_INDEX]);

            System.out.println(nodes[SOURCE_NODE_INDEX].getPoint().getAddress().toString());
            System.out.println(nodes[nodes.length - 1].getPoint().getAddress().toString());
            System.out.println(nodes[nodes.length - 2].getDistance());
            System.out.println(nodes[nodes.length - 1].getDistance());

            List<Node> shortestPath = nodes[nodes.length - 1].getShortestPath();

            List<MapPoint> optimizedRoute = new ArrayList<>(shortestPath.size());
            for (Node node : shortestPath) {
                optimizedRoute.add(node.getPoint());
            }

            return optimizedRoute;

        } catch (IOException e) {
            throw new MatrixBuildingException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Getting matrices process was interrupted", e);
        }
    }
}
