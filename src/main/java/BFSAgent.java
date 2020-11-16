import java.util.*;

public class BFSAgent implements SearchAgent {

    private State goal;
    private long runningTime;
    private List<String> expandedNodes;

    public BFSAgent(State initialState){
        Queue<State> frontier = new LinkedList<>();
        Set<State> explored = new HashSet<State>();
        long startTime = System.currentTimeMillis();
        frontier.add(initialState);
        while(!frontier.isEmpty()) {
            State s = frontier.remove();
            explored.add(s);
            expandedNodes.add(s.getState());
            if(s.isGoal()) {
                goal = s;
                break;
            }
            List<State> neighbors = s.getNeighbors();
            for(State n : neighbors) {
                if(!(frontier.contains(n) || explored.contains(n))) {
                    frontier.add(n);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        runningTime = endTime - startTime;
    }

    @Override
    public List<String> getPath() {
        List<String> path = new ArrayList<String>();
        State temp = goal;
        while(temp != null) {
            path.add(temp.getState());
            temp = temp.getParent();
        }
        Collections.reverse(path);
        return path;
    }

    @Override
    public int getCost() {
        return goal.getDepth();
    }

    @Override
    public List<String> getExpandedNodes() {
        return expandedNodes;
    }

    @Override
    public int getSearchDepth() {
        return goal.getDepth();
    }

    @Override
    public long runningTime() {
        return runningTime;
    }
}
