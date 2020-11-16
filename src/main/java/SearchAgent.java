import java.util.List;

public interface SearchAgent {


    /**
     * @return the path to reach the goal based on the agent algorithm
     */
    List<String> getPath();

    /**
     * @return the cost to reach the goal state or -1 if the goal is not reachable
     */
    int getCost();

    /**
     * @return all the nodes expanded in the search
     */
    List<String> getExpandedNodes();

    /**
     * @return the max depth the agent reached
     */
    int getSearchDepth();

    /**
     * @return search running time in milli seconds
     */
    long runningTime();

}
