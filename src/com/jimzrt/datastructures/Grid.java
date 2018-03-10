package com.jimzrt.datastructures;

import java.util.List;

public class Grid {
    private int size;
    private SearchNode[][] grid;

    public Grid(int size) {
        this.size = size;

        grid = new SearchNode[size][];
        for (int i = 0; i < size; i++) {
            grid[i] = new SearchNode[size];
        }
    }

    public void generateNodes() {
        //set nodes
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = new SearchNode(x, y, false);
            }
        }
        //set successors
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {

                SearchNode node = grid[x][y];
                if (x - 1 >= 0) {
                    node.addSuccessor(grid[x - 1][y]);
                }
                if (x + 1 <= size - 1) {
                    node.addSuccessor(grid[x + 1][y]);
                }
                if (y - 1 >= 0) {
                    node.addSuccessor(grid[x][y - 1]);
                }
                if (y + 1 <= size - 1) {
                    node.addSuccessor(grid[x][y + 1]);
                }
            }

        }
    }

    public void generateObstacles() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (Math.random() < 0.15) {
                    grid[x][y].setIsObstacle(true);
                }
            }
        }

    }

    public SearchNode getNode(int x, int y) {
        return grid[x][y];
    }

    public void printGrid() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                System.out.print("[" + (grid[x][y].isObstacle() ? "X" : " ") + "]");
            }
            System.out.println("");

        }
    }

    public void printPath(List<SearchNode> list) {
        if (list == null) {
            System.out.println("No path possible!");
            return;
        }
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int index = list.indexOf(grid[x][y]);
                if (index != -1) {
                    if (index == 0) {
                        System.out.print("[*]");
                    } else if (index == list.size() - 1) {
                        System.out.print("[*]");

                    } else {
                        SearchNode node = list.get(index);
                        SearchNode nextNode = list.get(index + 1);
                        if (nextNode.getX() > node.getX()) {
                            System.out.print("[↓]");
                        } else if (nextNode.getX() < node.getX()) {
                            System.out.print("[↑]");
                        } else if (nextNode.getY() > node.getY()) {
                            System.out.print("[→]");
                        } else {
                            System.out.print("[←]");
                        }
                    }
                } else {
                    System.out.print("[" + (grid[x][y].isObstacle() ? "X" : " ") + "]");
                }
            }
            System.out.println("");

        }
    }

}
