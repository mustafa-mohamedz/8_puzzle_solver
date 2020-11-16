import java.util.*;
import java.util.stream.Collectors;

public class DFSAgent implements SearchAgent {

    private final List<String> path;
    private final int cost;
    private final List<String> expandedNodes;
    private final long runningTime;
    private int maxDepth;

    public DFSAgent(String initialState) {
        long startTime = System.nanoTime();
        List<State> exploredList = search(new State(initialState, 0, null));
        long endTime = System.nanoTime();
        this.runningTime = (endTime - startTime) / 1000000;
        this.expandedNodes = exploredList.stream().map(State::getState).collect(Collectors.toList());
        State lastState = exploredList.get(exploredList.size() - 1);
        this.cost = lastState.isGoal() ? lastState.getDepth() : -1;

        this.path = new ArrayList<>();
        State parent = lastState.getParent();
        while (parent != null) {
            path.add(parent.getState());
            parent = parent.getParent();
        }
        Collections.reverse(path);
    }

    private List<State> search(State initialState) {
        Stack<State> frontier = new Stack<>();//create frontier which is a stack in DFS
        Set<State> frontierSet = new HashSet<>();//create frontier set to use it in contain check in O(1)
        frontier.push(initialState);//push the initial state in the stack
        frontierSet.add(initialState);//push the initial state in the stack
        Set<State> explored = new LinkedHashSet<>();//create explored set represented as LinkedHashSet to maintain order
        while (!frontier.empty()) {
            State currentState = frontier.pop();//get the current state
            frontierSet.remove(currentState);//remove it from frontier
            explored.add(currentState);//add it to explored list
            maxDepth = Math.max(maxDepth, currentState.getDepth());//update max depth
            if (currentState.isGoal()) return new ArrayList<>(explored);//terminate if goal found

            currentState.getNeighbors().forEach(neighbor -> {//iterate for each neighbor
                if (!frontierSet.contains(neighbor) && !explored.contains(neighbor)) { //if neighbor didn't explored or not in frontier set
                    frontier.push(neighbor);//add neighbor to frontier stack
                    frontierSet.add(neighbor);//add neighbor to frontier set
                }
            });
        }
        return new ArrayList<>(explored);//return the search path
    }


    public List<String> getPath() {
        return path;
    }

    public int getCost() {
        return cost;
    }

    public List<String> getExpandedNodes() {
        return expandedNodes;
    }

    public int getSearchDepth() {
        return maxDepth;
    }

    public long runningTime() {
        return runningTime;
    }
}
