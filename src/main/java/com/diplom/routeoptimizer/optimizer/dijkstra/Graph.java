package com.diplom.routeoptimizer.optimizer.dijkstra;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Graph {

    private final Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public void addNodes(Node[] nodes) {
        this.nodes.addAll(Arrays.asList(nodes));
    }

    public Set<Node> getNodes() {
        return nodes;
    }
}