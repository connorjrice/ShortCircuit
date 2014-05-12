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
    private ArrayList<Integer> neverReturnNodes = new ArrayList<Integer>();
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

    public Path getPath(String start, String end) {
        return getPath(Graph.getNode(start), Graph.getNode(end));
    }

    public Path getPath(GraphNode start, GraphNode end) {
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
            Path[] legalPaths = getLegalPaths(curPath);
            int legalIndex = 0;
            while (legalPaths[legalIndex] != null) {
                frontier.add(legalPaths[legalIndex]);
                if (legalPaths[legalIndex].getLastNode().equals(endNode)) {
                    clearPaths();
                    return legalPaths[legalIndex];
                }
                legalIndex++;
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
    

    public Path[] getLegalPaths(Path p) {
        Path[] legalPaths = new Path[10];
        int[] neighbors = Graph.getNeighbors(p.getLastNode().getIndex());
        int neighborIndex = 0;
        int legalIndex = 0;
        while (neighbors[neighborIndex] != 0) {
            GraphNode curNode = Graph.getNode(neighbors[neighborIndex]);
            if (!neverReturnNodes.contains(curNode.getIndex())) {
                if (!p.getGraphNodes().contains(curNode)) {
                    Path pNew = p.clone();
                    pNew.addNode(curNode);
                    pNew.updateCost(Heuristic.compareTo(curNode));
                    legalPaths[legalIndex] = pNew;
                    legalIndex++;
                }
            }
            neighborIndex++;
        }
        for (GraphNode curNode : p.getGraphNodes()) {
            neverReturnNodes.add(curNode.getIndex());
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
