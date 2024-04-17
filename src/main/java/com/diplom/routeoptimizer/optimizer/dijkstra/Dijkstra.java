package com.diplom.routeoptimizer.optimizer.dijkstra;

import java.math.BigDecimal;
import java.util.*;

public class Dijkstra {
    public static void calculateShortestPathFromSource(Node source) {
        source.setDistance(new BigDecimal("0.0"));

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);
        while (!unsettledNodes.isEmpty()) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, BigDecimal> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                BigDecimal edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private static void calculateMinimumDistance(Node evaluationNode,
                                                 BigDecimal edgeWeight, Node sourceNode) {
        BigDecimal sourceDistance = sourceNode.getDistance();
        if (sourceDistance.add(edgeWeight).compareTo(evaluationNode.getDistance()) < 0) {
            evaluationNode.setDistance(sourceDistance.add(edgeWeight));
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        BigDecimal lowestDistance = BigDecimal.valueOf(Double.MAX_VALUE);
        for (Node node : unsettledNodes) {
            BigDecimal nodeDistance = node.getDistance();
            if (nodeDistance.compareTo(lowestDistance) < 0) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
}

