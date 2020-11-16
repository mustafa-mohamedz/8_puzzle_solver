import java.util.ArrayList;
import java.util.List;

public class State {


    private final String state;
    private final int depth;
    private final State parent;

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

    public List<State> getNeighbors(){
        ArrayList<State> neighbors = new ArrayList<State>();
        State upNeighbor = up();
        if (upNeighbor != null) neighbors.add(upNeighbor);
        State downNeighbor = down();
        if (downNeighbor != null) neighbors.add(downNeighbor);
        State leftNeighbor = left();
        if (leftNeighbor != null) neighbors.add(leftNeighbor);
        State rightNeighbor = right();
        if (rightNeighbor != null) neighbors.add(rightNeighbor);
        return neighbors;
    }

    private State up(){
        int indexOfZero = state.indexOf('0');
        State ans = null;
        if (indexOfZero >= 3){
            ans = new State(swap(state,indexOfZero,indexOfZero - 3), depth +1,this);
        }
        return ans;
    }
    private State down(){
        int indexOfZero = state.indexOf('0');
        State ans = null;
        if (indexOfZero < 6){
            ans = new State(swap(state,indexOfZero,indexOfZero + 3), depth +1,this);
        }
        return ans;
    }
    private State left(){
        int indexOfZero = state.indexOf('0');
        State ans = null;
        if (indexOfZero%3 != 0){
            ans = new State(swap(state,indexOfZero,indexOfZero - 1), depth +1,this);
        }
        return ans;
    }
    private State right(){
        int indexOfZero = state.indexOf('0');
        State ans = null;
        if (indexOfZero%3 != 2){
            ans = new State(swap(state,indexOfZero,indexOfZero + 1), depth +1,this);
        }
        return ans;
    }



    public int getTotalManhattanCost(){
        int manhattanDistance = 0;
        for (int i = 0;i<9;i++){
            manhattanDistance += calculateManhattanDistance(i,state.indexOf((char) (i + '0')));
        }
        return manhattanDistance + this.depth;
    }

    private int calculateManhattanDistance(int i, int j) {
        int yi = i/3;
        int xi = i%3;
        int yj = j/3;
        int xj = j%3;
        return Math.abs(xi-xj)+Math.abs(yi - yj);
    }

    public double getTotalEuclideanCost(){
        double euclideanDistance = 0;
        for (int i = 0;i<9;i++){
            euclideanDistance += calculateEuclideanDistance(i,state.indexOf((char) (i + '0')));
        }
        return euclideanDistance + this.depth;
    }

    private double calculateEuclideanDistance(int i, int j) {
        int yi = i/3;
        int xi = i%3;
        int yj = j/3;
        int xj = j%3;
        return Math.sqrt((xi-xj) * (xi-xj) +(yi - yj) * (yi - yj));
    }

    private static String swap(String str, int i, int j){
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }

}
