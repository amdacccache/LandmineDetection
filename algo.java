package pathfinder;

import java.util.PriorityQueue;

public class algo {

    // cost for types of movement
    public static final int diagonalCost = 14;
    public static final int vert_horCost = 10;

    // cells
    private cell[][] grid;
    //put cells with lowest cost in first
    //Unvisited cells are nodes that need to be evaluated
    private PriorityQueue<cell> unvisitedCells;
    // visited cells have already been evaluated
    private boolean[][] visitedCells;
    //Starting point
    private int startX, startY;
    //ending point
    private int endX, endY;

    public algo(int width, int height, int sx, int sy, int ex, int ey, int[][] blocks) {
        grid = new cell[width][height];
        visitedCells = new boolean[width][height];
        unvisitedCells = new PriorityQueue<cell>((cell c1, cell c2) -> {
            return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
        });

        startCell(sx, sy);
        endCell(ex, ey);

        // init heuristics and cells
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y] = new cell(x, y);
                grid[x][y].hCost = Math.abs(x - endX) + Math.abs(y - endY);
                grid[x][y].solution = false;
            }
        }

        grid[startX][startY].finalCost = 0;

        for (int x = 0; x < blocks.length; x++) {
            addBlock(blocks[x][0], blocks[x][1]);
        }
    }

    public void addBlock(int x, int y) {
        grid[x][y] = null;
    }

    public void startCell(int x, int y) {
        startX = x;
        startY = y;
    }

    public void endCell(int x, int y) {
        endX = x;
        endY = y;
    }

    public void updateCost(cell current, cell t, int cost) {
        if (t == null || visitedCells[t.x][t.y])
            return;

        int tFinalCost = t.hCost + cost;
        boolean isOpen = unvisitedCells.contains(t);

        if (!isOpen || tFinalCost < t.finalCost) {
            t.finalCost = tFinalCost;
            t.parent = current;

            if (!isOpen)
                unvisitedCells.add(t);
        }
    }

    public void process() {
        // we addthe starting location to unvisted list
        unvisitedCells.add(grid[startX][startY]);
        cell current;

        while(true) {
            current = unvisitedCells.poll();

            if (current == null)
                break;

            visitedCells[current.x][current.y] = true;

            if (current.equals(grid[endX][endY]))
                return;

            cell t;
            if (current.x - 1 >=0) {
                t = grid[current.x - 1][current.y];
                updateCost(current, t, current.finalCost + vert_horCost);

                if (current.y - 1 >= 0) {
                    t = grid[current.x - 1][current.y - 1];
                    updateCost(current, t, current.finalCost + diagonalCost);
                }

                if (current.y + 1 < grid[0].length) {
                    t = grid[current.x - 1][current.y - 1];
                    updateCost(current, t, current.finalCost + diagonalCost);
                }
            }

            if (current.y - 1 >= 0) {
                t = grid[current.x][current.y - 1];
                updateCost(current, t, current.finalCost + vert_horCost);
            }

            if (current.y + 1 < grid[0].length) {
                t = grid[current.x][current.y + 1];
                updateCost(current, t, current.finalCost + vert_horCost);
            }

            if (current.x + 1 < grid.length) {
                t = grid[current.x + 1][current.y];
                updateCost(current, t, current.finalCost + vert_horCost);

                if (current.y - 1 >= 0) {
                    t = grid[current.x + 1][current.y - 1];
                    updateCost(current, t, current.finalCost + diagonalCost);
                }

                if (current.y + 1 < grid[0].length) {
                    t = grid[current.x + 1][current.y + 1];
                    updateCost(current, t, current.finalCost + diagonalCost);
                }
            }
        }
    }

    public void display() {
        System.out.println("Grid: ");

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (x == startX && y == startY)
                    System.out.print("SO  "); // source cell
                else if (x == endX && y == endY)
                    System.out.print("DE  "); // destination cell
                else if (grid[x][y] != null)
                    System.out.printf("%-3d ", 0);
                else
                    System.out.print("BL "); // block cell
            }

            System.out.println();
        }

        System.out.println();
    }

    public void displayScore() {
        System.out.println("\nScores for cells: ");

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] != null)
                    System.out.printf("%-3d ", grid[x][y].finalCost);
                else
                    System.out.print("BL ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displaySolution() {
        if (visitedCells[endX][endY]) {
            // we track back path
            System.out.println("Path :");
            cell current = grid[endX][endY];
            System.out.println(current);
            grid[current.x][current.y].solution = true;

            while(current.parent != null) {
                System.out.println("-> " + current.parent);
                grid[current.parent.x][current.parent.y].solution = true;
                current = current.parent;
            }

            System.out.println("\n");

            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[x].length; y++) {
                    if (x == startX && y == startY)
                        System.out.print("SO  "); // source cell
                    else if (x == endX && y == endY)
                        System.out.print("DE  "); // destination cell
                    else if (grid[x][y] != null)
                        System.out.printf("%-3d ", grid[x][y].solution ? "X" : "0");
                    else
                        System.out.print("BL "); // block cell
                }

                System.out.println();
            }

            System.out.println();
        } else
            System.out.println("No possible path");
    }
}
