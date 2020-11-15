import java.util.List;

public interface SearchAgent {
    List<String> getPath();
    int getCost();
    List<String> getExpandedNodes();
    int getSearchDepth();
    long runningTime();

}
