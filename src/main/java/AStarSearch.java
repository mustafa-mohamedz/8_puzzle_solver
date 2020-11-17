import java.util.*;
import java.util.stream.Collectors;

public class AStarSearch implements SearchAgent {
    private final List<String> path;
    private final int cost;
    private final List<String> expandedNodes;
    private final long runningTime;
    private int maxDepth;

    public AStarSearch(String initialState, String distanceType) {
        long startTime = System.nanoTime();
        List<State> exploredList = Search(new State(initialState, 0, null), distanceType);
        long endTime = System.nanoTime();
        this.runningTime = (endTime - startTime) / 1000000;
        this.expandedNodes = exploredList.stream().map(State::getState).collect(Collectors.toList());
        State lastState = exploredList.get(exploredList.size() - 1);
        this.cost = lastState.isGoal() ? lastState.getDepth() : -1;

        this.path = new ArrayList<>();
        if (lastState.isGoal()) {
            this.path.add(lastState.getState());
            State parent = lastState.getParent();
            while (parent != null) {
                path.add(parent.getState());
                parent = parent.getParent();
            }
            Collections.reverse(path);
        }
    }

    private List<State> Search(State initialState, String distanceType) {
        Queue<State> frontier = new PriorityQueue<>(distanceType.equals("manhattan") ?
                Comparator.comparing(State::getTotalManhattanCost)
                : Comparator.comparing(State::getTotalEuclideanCost));
        Set<State> explored = new LinkedHashSet<>();
        Map<String, State> frontierMap = new HashMap<>();//map to easy access element of the frontier
        frontier.add(initialState);
        frontierMap.put(initialState.getState(), initialState);
        while (!frontier.isEmpty()) {
            State state = frontier.remove();
            frontierMap.remove(state.getState());
            maxDepth = Math.max(maxDepth, state.getDepth());//update max depth
            explored.add(state);
            if (state.isGoal()) {
                return new ArrayList<>(explored);
            }
            List<State> neighbors = state.getNeighbors();
            for (State neighbor : neighbors) {
                if (!frontierMap.containsKey(neighbor.getState()) && !explored.contains(neighbor)) {
                    frontier.add(neighbor);
                    frontierMap.put(neighbor.getState(), neighbor);
                } else if (frontierMap.containsKey(neighbor.getState())) {
                    //get the copy of this state that exists in the frontier
                    State old = frontierMap.get(neighbor.getState());
                    //check the if the total cost of the old path is greeter than the new path if so update the node in the frontier
                    if (distanceType.equals("manhattan")) {
                        if (old.getTotalManhattanCost() > neighbor.getTotalManhattanCost()) {
                            frontier.remove(old);
                            frontierMap.remove(old.getState());
                            frontier.add(neighbor);
                            frontierMap.put(neighbor.getState(), neighbor);
                        }
                    } else {
                        if (old.getTotalEuclideanCost() > neighbor.getTotalEuclideanCost()) {
                            frontier.remove(old);
                            frontierMap.remove(old.getState());
                            frontier.add(neighbor);
                            frontierMap.put(neighbor.getState(), neighbor);
                        }
                    }
                }
            }
        }
        return new ArrayList<>(explored);
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
