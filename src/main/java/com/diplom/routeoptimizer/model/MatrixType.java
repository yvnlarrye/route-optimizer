package com.diplom.routeoptimizer.model;

public enum MatrixType {

    DISTANCES("distances"), TIMES("times"), WEIGHTS("weights");

    final String name;

    MatrixType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
