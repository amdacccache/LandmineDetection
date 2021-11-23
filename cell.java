package pathfinder;

// defining a cell within gridmap
public class cell {

    // grid coordinates
    public int x, y;
    // parent cell
    public cell parent;
    // hueristic cost of cell
    public int hCost;
    // final cost
    public int finalCost;
    // G+H where
    // G(n) = cost of path from start node to node n
    // H(n) = heuristid that estimates cost of cheapes path from n to end
    public boolean solution;

    public cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y +"]";
    }
}
