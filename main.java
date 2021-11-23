package pathfinder;

public class main {

    public static void main(String[] args) {
        algo astar = new algo(5, 5, 0, 0, 3, 2,
                new int[][] {
                        {0,4}, {2,2}, {3,1}, {3,3}, {2,1}, {2,3}
                }
        );
        astar.display();
        astar.process(); // Apply Algo
        astar.displayScore(); // Display score
        astar.displaySolution(); // display path solution
    }
}
