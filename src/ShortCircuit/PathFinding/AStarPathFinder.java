package ShortCircuit.PathFinding;

import ShortCircuit.DataStructures.Graph;
import ShortCircuit.DataStructures.Interfaces.Heuristic;
import ShortCircuit.DataStructures.Interfaces.PathFinder;
import ShortCircuit.DataStructures.Objects.Path;
import java.util.ArrayList;

/**
 * ASKMATTHEW: getLegalPaths threading?
 * @author Connor Rice and Matthew Whitehead
 */
public class AStarPathFinder implements PathFinder {

    private int endIndex;
    private boolean maxFlag;
    private int maxNodeSize;
    private Heuristic Heuristic;
    private Graph Graph;
    private ArrayList<Integer> neverReturnNodes = new ArrayList<Integer>();
    private ArrayList<Path> frontier = new ArrayList<Path>();
    private ArrayList<Path> closed = new ArrayList<Path>();
    private int numRecursions;
    private int maxRecursions = 75;

    public AStarPathFinder(Heuristic Heuristic, Graph Graph, int nodeSize) {
        this.Heuristic = Heuristic;
        this.Graph = Graph;
        this.maxNodeSize = nodeSize;
        numRecursions = 0;
    }

    public Path getPath(String start, String end) {
        return getPath(Graph.getNode(start).getIndex(), Graph.getNode(end).getIndex());
    }

    public Path getPath(int start, int end) {
        this.endIndex = end;
        Heuristic.setEndPosition((Graph.getNode(endIndex).getElement()));
        maxFlag = false;
        numRecursions = 0;
        return pathFind(createFirstPath(start));
    }

    public Path createFirstPath(int firstNode) {
        ArrayList<Integer> firstNodes = new ArrayList<Integer>();
        firstNodes.add(firstNode);
        Path firstPath = new Path(firstNodes);
        firstPath.updateCost(Heuristic.compareTo(Graph.getNode(firstNode)));
        return firstPath;
    }

    public Path pathFind(Path curPath) {
        if (!maxFlag) {
            frontier.add(curPath);
            closed.add(curPath);
            Path nextPath = getNextPath();
            ArrayList<Path> legalPaths = getLegalPaths(curPath);
            for (Path legalPath : legalPaths) {
                frontier.add(legalPath);
            }
            if (numRecursions < maxRecursions) {
                frontier.remove(curPath);
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
    }
    
    private void clearPaths() {
        frontier.clear();
        closed.clear();
        neverReturnNodes.clear();
    }
    

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
        Path cheapestPath = frontier.get(0);
        for (Path curPath : frontier) {
            if (curPath.getCost() < cheapestPath.getCost()) {
                if (!closed.contains(curPath)) { //ASKMATTHEW: can we do like a hashmap or something?
                    cheapestPath = curPath;
                }
            }
        }
        if (cheapestPath.getGraphNodes().size() > maxNodeSize) {
            maxFlag = true;
        }

        frontier.remove(cheapestPath);
        return cheapestPath;
    }

    public Path clonePath(Path p) {
        ArrayList<Integer> pathClone = new ArrayList<Integer>();
        for (int curNode : p.getGraphNodes()) {
            pathClone.add(curNode);
        }
        return new Path(pathClone);
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}
