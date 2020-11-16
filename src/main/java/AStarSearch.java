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
        this.path.add(lastState.getState());
        State parent = lastState.getParent();
        while (parent != null) {
            path.add(parent.getState());
            parent = parent.getParent();
        }
        Collections.reverse(path);
    }

    private List<State> Search(State initialState, String distanceType) {
        Queue<State> frontier = new PriorityQueue<>(distanceType.equals("manhattan") ?
                Comparator.comparing(State::getTotalManhattanCost)
                : Comparator.comparing(State::getTotalEuclideanCost));
        Set<State> explored = new LinkedHashSet<>();
        Set<State> frontierSet = new HashSet<>();
        frontier.add(initialState);
        frontierSet.add(initialState);
        while (!frontier.isEmpty()) {
            State state = frontier.remove();
            frontierSet.remove(state);
            maxDepth = Math.max(maxDepth, state.getDepth());//update max depth
            explored.add(state);
            if (state.isGoal()) {
                return new ArrayList<>(explored);
            }
            List<State> neighbors = state.getNeighbors();
            for (State neighbor : neighbors) {
                if (!frontierSet.contains(neighbor) && !explored.contains(neighbor)) {
                    frontier.add(neighbor);
                    frontierSet.add(neighbor);
                } else if (frontierSet.contains(neighbor)) {
                    //TODO discuss
                    frontier.remove(neighbor);
                    frontierSet.remove(neighbor);
                    frontier.add(neighbor);
                    frontierSet.add(neighbor);
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

    public static void main(String[] args) {
        SearchAgent searchAgent = new AStarSearch("125340678", "manhattan");
        searchAgent.getPath().forEach(System.out::println);
        System.out.println(searchAgent.getCost());
        System.out.println(searchAgent.getSearchDepth());
        System.out.println(searchAgent.runningTime());
        searchAgent.getExpandedNodes().forEach(System.out::println);
    }
}
