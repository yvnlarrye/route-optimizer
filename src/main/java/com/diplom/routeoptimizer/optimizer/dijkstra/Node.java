package com.diplom.routeoptimizer.optimizer.dijkstra;

import com.diplom.routeoptimizer.model.MapPoint;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Node {

    private MapPoint point;

    private List<Node> shortestPath = new LinkedList<>();

    private BigDecimal distance = BigDecimal.valueOf(Double.MAX_VALUE);

    Map<Node, BigDecimal> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, BigDecimal distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(MapPoint point) {
        this.point = point;
    }

    public MapPoint getPoint() {
        return point;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Map<Node, BigDecimal> getAdjacentNodes() {
        return adjacentNodes;
    }
}