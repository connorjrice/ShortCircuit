package ShortCircuit.PathFinding;

import ShortCircuit.DataStructures.Graph;
import ShortCircuit.DataStructures.Interfaces.Heuristic;
import ShortCircuit.DataStructures.Interfaces.PathFinder;
import ShortCircuit.DataStructures.Nodes.GraphNode;
import ShortCircuit.DataStructures.Objects.Path;
import java.util.ArrayList;

/**
 *
 * @author Connor Rice and Matthew Whitehead
 */
public class AStarPathFinder implements PathFinder {

    private GraphNode endNode;
    private boolean maxFlag;
    private int maxNodeSize;
    private Heuristic Heuristic;
    private Graph Graph;
    private boolean neverReturn = true;
    private ArrayList<GraphNode> neverReturnNodes = new ArrayList<GraphNode>();
    private ArrayList<Path> frontier = new ArrayList<Path>();
    private ArrayList<Path> closed = new ArrayList<Path>();
    private int numRecursions;
    private int maxRecursions = 1000;

    public AStarPathFinder(Heuristic Heuristic, Graph Graph, int nodeSize) {
        this.Heuristic = Heuristic;
        this.Graph = Graph;
        this.maxNodeSize = nodeSize;
        numRecursions = 0;
    }

    public Path initPathFinder(String start, String end) {
        return initPathFinder(Graph.getNode(start), Graph.getNode(end));
    }

    public Path initPathFinder(GraphNode start, GraphNode end) {
        this.endNode = end;
        Heuristic.setEndPosition((endNode.getElement()));
        maxFlag = false;
        return pathFind(createFirstPath(start));
    }

    public Path createFirstPath(GraphNode firstNode) {
        ArrayList<GraphNode> firstNodes = new ArrayList<GraphNode>();
        firstNodes.add(firstNode);
        Path firstPath = new Path(firstNodes);
        firstPath.updateCost(Heuristic.compareTo(firstNode));
        return firstPath;
    }

    public Path pathFind(Path curPath) {
        frontier.add(curPath);
        closed.add(curPath);
        if (!maxFlag) {
            Path nextPath = getNextPath();
            ArrayList<Path> legalPaths = getLegalPaths(curPath);
            for (Path legalPath : legalPaths) {
                frontier.add(legalPath);
                if (legalPath.getLastNode().equals(endNode)) {
                    clearPaths();
                    return legalPath;
                }
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
        int[] neighbors = Graph.getNeighbors(p.getLastNode().getIndex());
        int arrayIndex = 0;
        while (neighbors[arrayIndex] != 0) {
            if (!neverReturnNodes.contains(Graph.getNode(neighbors[arrayIndex]))) {
                if (!p.getGraphNodes().contains(Graph.getNode(neighbors[arrayIndex]))) {
                    Path pNew = p.clone();
                    pNew.addNode(Graph.getNode(neighbors[arrayIndex]));
                    pNew.updateCost(Heuristic.compareTo(Graph.getNode(neighbors[arrayIndex])));
                    legalPaths.add(pNew);
                }
            }
            arrayIndex++;
        }
        for (GraphNode curNode : p.getGraphNodes()) {
            neverReturnNodes.add(curNode);
        }
        p.setMarked();

        return legalPaths;
    }

    public Path getNextPath() {
        Path cheapestPath = frontier.get(0);
        for (Path curPath : frontier) {
            if (curPath.getCost() < cheapestPath.getCost()) {
                if (!closed.contains(curPath)) {
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
        ArrayList<GraphNode> pathClone = new ArrayList<GraphNode>();
        for (GraphNode curNode : p.getGraphNodes()) {
            pathClone.add(curNode);
        }
        return new Path(pathClone);
    }

    public void setEndNode(GraphNode endNode) {
        this.endNode = endNode;
    }

    public GraphNode getEndNode() {
        return endNode;
    }
}
