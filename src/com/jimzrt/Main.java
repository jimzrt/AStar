package com.jimzrt;

import com.jimzrt.datastructures.Coordinate;
import com.jimzrt.datastructures.SearchNode;
import com.jimzrt.solver.AStar;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        AStar aStar = new AStar(20);
        aStar.generateRandomObstacles();
        aStar.printGrid();

        ArrayList<SearchNode> shortestPath = aStar.shortestPath(new Coordinate(1, 1), new Coordinate(14, 18));
        aStar.printPath(shortestPath);
    }
}
