package ShortCircuit.PathFinding;

import ShortCircuit.DataStructures.Graph;
import ShortCircuit.DataStructures.MinHeap;
import java.util.ArrayList;

/**
 * @author Connor Rice and Matthew Whitehead
 */
public class AStarPathFinder implements PathFinder {

    private boolean maxFlag;
    private int maxNodeSize;
    private Heuristic Heuristic;
    private Graph Graph;
    private ArrayList<Integer> neverReturnNodes = new ArrayList<Integer>();
    private MinHeap<Path> frontier = new MinHeap<Path>();
    private int numRecursions;
    private int maxRecursions = 50;
    private long neverLong;
    
    public AStarPathFinder(Heuristic Heuristic, Graph Graph, int nodeSize) {
        this.Heuristic = Heuristic;
        this.Graph = Graph;
        this.maxNodeSize = nodeSize;
        numRecursions = 0;
        neverLong = 0;
    }

    public Path getPath(String start, String end) {
        return getPath(Graph.getIndex(start), Graph.getIndex(end));
    }

    public Path getPath(int start, int end) {
        Heuristic.setEndPosition((Graph.getElement(end)));
        maxFlag = false;
        numRecursions = 0;
        return pathFind(createFirstPath(start));
    }

    public Path createFirstPath(int firstNode) {
        ArrayList<Integer> firstNodes = new ArrayList<Integer>();
        firstNodes.add(firstNode);
        Path firstPath = new Path(firstNodes);
        firstPath.updateCost(Heuristic.compareTo(Graph.getNode(firstNode)));
        frontier.add(firstPath);
        return firstPath;
    }

    public Path pathFind(Path curPath) {
        if (!maxFlag) {
            Path nextPath = getNextPath();
            if (nextPath != null) {
                ArrayList<Path> legalPaths = getLegalPaths(curPath);
                for (Path legalPath : legalPaths) {
                    frontier.add(legalPath);
                }
                if (numRecursions < maxRecursions) {
                    numRecursions++;
                    return pathFind(nextPath);
                } else {
                    clearPaths();
                    return nextPath;
                }
            } else {
                clearPaths();
                return curPath;
            }
        } else {
            clearPaths();
            return curPath;
        }
    }
    
    private void clearPaths() {
        frontier.clear();
        neverReturnNodes.clear();
        neverLong = 0;
    }
    /*
     * Replace contains and neverReturnNodes
     */
    private boolean getInNever(int nodeIndex) {
        int pos = 1 << nodeIndex;
        return  (neverLong & pos) > 0;
    }
    
    private void setNever(int nodeIndex) {
        int pos = 1 << nodeIndex;
        neverLong = neverLong | pos;
    }

    // TODO: maybe try value threshold for adding to legalpath
    public ArrayList<Path> getLegalPaths(Path p) {
        ArrayList<Path> legalPaths = new ArrayList<Path>();
        int[] neighbors = Graph.getNeighbors(p.getLastNode());
        int arrayIndex = 0;
        while (neighbors[arrayIndex] != 0) {
            if (!neverReturnNodes.contains(neighbors[arrayIndex])) {
                Path pNew = p.clone();
                pNew.addNode(neighbors[arrayIndex]);
                pNew.updateCost(Heuristic.compareTo(Graph.getNode(neighbors[arrayIndex])));
                legalPaths.add(pNew);
            }
            arrayIndex++;
        }
        for (int curNode : p.getGraphNodes()) {
            neverReturnNodes.add(curNode);
        }
        p.setMarked();
        return legalPaths;
    }
    

    public Path getNextPath() {
        if (!frontier.isEmpty()) {
            Path cheapestPath = (Path) frontier.remove();
            if (cheapestPath.getGraphNodes().size() > maxNodeSize) {
                maxRecursions += 1;
                maxNodeSize += 1;
                maxFlag = true;
            }
            return cheapestPath;
        } else {
            return null;
        }
    }

    public Path clonePath(Path p) {
        ArrayList<Integer> pathClone = new ArrayList<Integer>();
        for (int curNode : p.getGraphNodes()) {
            pathClone.add(curNode);
        }
        return new Path(pathClone);
    }
    
}
