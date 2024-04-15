package com.diplom.routeoptimizer.optimizer.dijkstra;

import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.model.MapPoint;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Node {

    private MapPoint point;

    private List<Node> shortestPath = new LinkedList<>();

    private Double distance = Double.MAX_VALUE;

    Map<Node, Double> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, double distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(MapPoint point) {
        this.point = point;
    }

    public MapPoint getPoint() {
        return point;
    }

    public void setPoint(MapPoint point) {
        this.point = point;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Map<Node, Double> getAdjacentNodes() {
        return adjacentNodes;
    }
}