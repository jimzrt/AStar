package com.jimzrt.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchNode {
    private int x;
    private int y;
    private boolean isObstacle;
    private SearchNode parent;
    private double g;
    private List<SearchNode> successors = new ArrayList<>();

    SearchNode(int x, int y, boolean isObstacle) {
        this.x = x;
        this.y = y;
        this.isObstacle = isObstacle;
    }

    public List<SearchNode> getSuccessors() {
        return successors;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void addSuccessor(SearchNode successor) {
        this.successors.add(successor);
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchNode that = (SearchNode) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    public double getG() {
        return this.g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getF(Coordinate goal) {
        return getG() + getH(goal);
    }

    private double getH(Coordinate goal) {

        return dist(goal.getX(), goal.getY());

    }

    private double dist(int otherX, int otherY) {
        return Math.sqrt(Math.pow(this.x - otherX, 2) + Math.pow(this.y - otherY, 2));
    }

    public SearchNode getParent() {
        return parent;
    }

    public void setParent(SearchNode parent) {
        this.parent = parent;
    }

    public void setIsObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }
}
