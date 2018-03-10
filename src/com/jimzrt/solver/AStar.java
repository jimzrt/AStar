package com.jimzrt.solver;

import com.jimzrt.datastructures.Coordinate;
import com.jimzrt.datastructures.Grid;
import com.jimzrt.datastructures.SearchNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    private int size;
    private Grid grid;
    private SearchNode startNode;
    private SearchNode goalNode;

    public AStar(int size) {
        this.size = size;
        System.out.println("New grid with size " + size);
        grid = new Grid(size);
        grid.generateNodes();
    }

    private static ArrayList<SearchNode> path(SearchNode node) {
        ArrayList<SearchNode> path = new ArrayList<>();
        path.add(node);
        SearchNode currentNode = node;
        while (currentNode.getParent() != null) {
            SearchNode parent = currentNode.getParent();
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void setStartNode(Coordinate coordinate) {
        startNode = grid.getNode(coordinate.getX(), coordinate.getY());
        startNode.setIsObstacle(false);
        System.out.print("Setting start node: ");
        System.out.println(startNode);

    }

    private void setGoalNode(Coordinate coordinate) {
        goalNode = grid.getNode(coordinate.getX(), coordinate.getY());
        goalNode.setIsObstacle(false);
        System.out.print("Setting goal node: ");
        System.out.println(goalNode);
    }

    public ArrayList<SearchNode> shortestPath(Coordinate start, Coordinate goal) {
        if (start.getX() >= size || start.getY() >= size || start.getX() < 0 || start.getY() < 0) {
            System.out.println("goal node coordinates out of grid bounds!");
            return null;
        }
        if (goal.getX() >= size || goal.getY() >= size || goal.getX() < 0 || goal.getY() < 0) {
            System.out.println("goal node coordinates out of grid bounds!");
            return null;
        }

        SearchNode endNode = this.search(start, goal);
        if (endNode == null)
            return null;
        //return shortest path according to AStar heuristics
        return AStar.path(endNode);
    }

    private SearchNode search(Coordinate start, Coordinate goal) {
        setStartNode(start);
        setGoalNode(goal);

        PriorityQueue<SearchNode> openSet = new PriorityQueue<>(new AStar.SearchNodeComparator(goal));
        openSet.add(startNode);
        ArrayList<SearchNode> closedSet = new ArrayList<>();
        // current iteration of the search
        int numSearchSteps = 0;
        System.out.println("Step " + numSearchSteps + ": adding start node to open set");
        while (openSet.size() > 0) {
            //get element with the least sum of costs from the initial node
            //and heuristic costs to the goal
            SearchNode currentNode = openSet.poll();

            if (goalNode.equals(currentNode)) {
                System.out.println("Step " + numSearchSteps + ": Found goal!");
                //we know the shortest path to the goal node, done
                return currentNode;
            }
            //get successor nodes
            System.out.println("Step " + numSearchSteps + ": getting successor nodes of " + currentNode);
            List<SearchNode> successorNodes = currentNode.getSuccessors();
            for (SearchNode successorNode : successorNodes) {
                boolean inOpenSet;
                if (closedSet.contains(successorNode)) {
                    System.out.println("Step " + numSearchSteps + ": node " + successorNode + " already in closed set");
                    continue;
                }
                if (successorNode.isObstacle()) {
                    continue;
                }

                inOpenSet = openSet.contains(successorNode);

                //compute tentativeG
                double tentativeG = currentNode.getG() + 1;
                //node was already discovered and this path is worse than the last one
                if (inOpenSet && tentativeG >= successorNode.getG()) {
                    System.out.println("Step " + numSearchSteps + ": successor node " + successorNode + " was already discovered and this path is worse than the last one");
                    continue;

                }
                successorNode.setParent(currentNode);
                if (inOpenSet) {
                    // if successorNode is already in data structure it has to be inserted again to
                    // regain the order
                    System.out.println("Step " + numSearchSteps + ": successor node " + successorNode + " updated in open set");
                    openSet.remove(successorNode);
                    successorNode.setG(tentativeG);
                    openSet.add(successorNode);
                } else {
                    System.out.println("Step " + numSearchSteps + ": successor node " + successorNode + " added to open set");
                    successorNode.setG(tentativeG);
                    openSet.add(successorNode);
                }
            }
            closedSet.add(currentNode);
            numSearchSteps += 1;
        }
        return null;
    }

    public void printGrid() {
        grid.print();
    }

    public void printPath(ArrayList<SearchNode> shortestPath) {
        grid.printPath(shortestPath);
    }

    public void generateRandomObstacles() {
        System.out.println("Generating random obstacles");
        grid.generateObstacles();
    }

    public static class SearchNodeComparator implements Comparator<SearchNode> {
        Coordinate goal;

        SearchNodeComparator(Coordinate goal) {
            this.goal = goal;
        }

        public int compare(SearchNode node1, SearchNode node2) {
            return Double.compare(node1.getF(goal), node2.getF(goal));
        }
    }

}
