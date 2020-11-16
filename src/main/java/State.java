import java.util.ArrayList;
import java.util.List;

public class State {

    /*our representation of the state of the puzzle
    for example the state
    *---*---*---*
    | 1 |   | 5 |
    *---*---*---*
    | 2 | 4 | 3 |
    *---*---*---*
    | 8 | 7 | 6 |
    *---*---*---*
    is represented as the string "105243876"
     */
    private final String state;
    private final int depth;      //the length of the path from initial state to this state
    private final State parent;   //pointer to the parent state

    //we implenent equals and hash code to use State in data structures
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state1 = (State) o;
        return state.equals(state1.state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    public State(String state , int depth, State parent){
        this.state = state;
        this.depth = depth;
        this.parent = parent;
    }

    public String getState() {
        return state;
    }

    public int getDepth() {
        return depth;
    }

    public State getParent() {
        return parent;
    }

    /***
     *
     * @return list of all states we can reach from one move
     */
    public List<State> getNeighbors() {
        ArrayList<State> neighbors = new ArrayList<>();
        State upNeighbor = up();
        if (upNeighbor != null) neighbors.add(upNeighbor);
        State rightNeighbor = right();
        if (rightNeighbor != null) neighbors.add(rightNeighbor);
        State downNeighbor = down();
        if (downNeighbor != null) neighbors.add(downNeighbor);
        State leftNeighbor = left();
        if (leftNeighbor != null) neighbors.add(leftNeighbor);
        return neighbors;
    }

    /***
     *
     * @return state we can reach from moving empty square up
     */
    private State up() {
        int indexOfZero = state.indexOf('0');
        State ans = null;
        if (indexOfZero >= 3) {
            ans = new State(swap(state, indexOfZero, indexOfZero - 3), depth + 1, this);
        }
        return ans;
    }

    /***
     *
     * @return state we can reach from moving empty square down
     */
    private State down() {
        int indexOfZero = state.indexOf('0');
        State ans = null;
        if (indexOfZero < 6) {
            ans = new State(swap(state, indexOfZero, indexOfZero + 3), depth + 1, this);
        }
        return ans;
    }

    /***
     *
     * @return state we can reach from moving empty square left
     */
    private State left() {
        int indexOfZero = state.indexOf('0');
        State ans = null;
        if (indexOfZero % 3 != 0) {
            ans = new State(swap(state, indexOfZero, indexOfZero - 1), depth + 1, this);
        }
        return ans;
    }

    /***
     *
     * @return state we can reach from moving empty square right
     */
    private State right() {
        int indexOfZero = state.indexOf('0');
        State ans = null;
        if (indexOfZero % 3 != 2) {
            ans = new State(swap(state, indexOfZero, indexOfZero + 1), depth + 1, this);
        }
        return ans;
    }


    /***
     *
     * @return the cost to get to this node plus its manhattan distance from the goal
     */
    public int getTotalManhattanCost() {
        int manhattanDistance = 0;
        for (int i = 0; i < 9; i++) {
            manhattanDistance += calculateManhattanDistance(i, state.indexOf((char) (i + '0')));
        }
        return manhattanDistance + this.depth;
    }

    /***
     * calculates the manhattan distance between two locations in our representation of the puzzle state
     * @param i first location
     * @param j second location
     * @return the manhattan distance between two locations i,j
     */
    private int calculateManhattanDistance(int i, int j) {
        int yi = i / 3;
        int xi = i % 3;
        int yj = j / 3;
        int xj = j % 3;
        return Math.abs(xi - xj) + Math.abs(yi - yj);
    }

    /***
     *
     * @return the cost to get to this node plus its euclidean distance from the goal
     */
    public double getTotalEuclideanCost() {
        double euclideanDistance = 0;
        for (int i = 0; i < 9; i++) {
            euclideanDistance += calculateEuclideanDistance(i, state.indexOf((char) (i + '0')));
        }
        return euclideanDistance + this.depth;
    }

    /***
     * calculates the euclidean distance between two locations in our representation of the puzzle state
     * @param i first location
     * @param j second location
     * @return the euclidean distance between two locations i,j
     */
    private double calculateEuclideanDistance(int i, int j) {
        int yi = i / 3;
        int xi = i % 3;
        int yj = j / 3;
        int xj = j % 3;
        return Math.sqrt((xi - xj) * (xi - xj) + (yi - yj) * (yi - yj));
    }

    /***
     *
     * @return true if the current state is the goal state ,otherwise false
     */
    boolean isGoal() {
        return state.equals("012345678");
    }

    /***
     * swaps two chars in string
     * @param str the string containing two chars we want to swap
     * @param i index of first char
     * @param j index of second char
     * @return the string str after we swap the two chars in it.
     */
    private static String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }

}
