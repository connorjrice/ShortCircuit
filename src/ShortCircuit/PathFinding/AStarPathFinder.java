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
    private Path finalPath;
    private Heuristic Heuristic;
    private Graph Graph;
    private boolean neverReturn = true;
    private ArrayList<GraphNode> neverReturnNodes = new ArrayList<GraphNode>();
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
    
    public Path pathFind2(Path curPath) {
        frontier.add(curPath);
        if (!maxFlag) {
            if (!curPath.getMarked()) {
                Path nextPath = getNextPath();
                ArrayList<Path> legalPaths = getLegalPaths(curPath);
                for (Path legalPath : legalPaths) {
                    frontier.add(legalPath);
                    if (legalPath.getLastNode().equals(endNode)) {
                        finalPath = legalPath;
                    }
                }
            } else {
                closed.add(curPath);
                return pathFind2(getNextPath());
            }
        } else {
            return getNextPath();
        }
        return null;
    }

    public Path pathFind(Path curPath) {
        if (endNode != null) { // precautionary
            frontier.add(curPath);
            closed.add(curPath);

            /**
             * maxFlag allows the pathfinding to be broken into smaller chunks.
             */
            if (!maxFlag) { 
                Path nextPath = getNextPath();            
                ArrayList<Path> legalPaths = getLegalPaths(curPath);
                for (Path legalPath : legalPaths) {
                    frontier.add(legalPath);
                    if (legalPath.getLastNode().equals(endNode)) {
                        finalPath = legalPath;
                    }
                }
                
                /**
                 * If we haven't reached the final node, call pathFind again.
                 */
                if (numRecursions < maxRecursions) {
                    frontier.remove(curPath);
                    numRecursions++;
                    if (finalPath == null) {
                        return pathFind(nextPath);
                    } else {
                        finalPath.setComplete();
                        return finalPath;
                    }
                } else {
                    return getNextPath();
                }
                    /**
             * However, if the maximum number of nodes in a given path has been
             * met, we return the next path so that new pathfinding can happen.
             */    
            } else {
                frontier.remove(curPath);
                return getNextPath();
            }

        } else {
            return null;
        }
    }

    public ArrayList<Path> getLegalPaths(Path p) {
        ArrayList<Path> legalPaths = new ArrayList<Path>();
        ArrayList<GraphNode> neighbors = Graph.getNeighbors(p.getLastNode().getIndex());
        for (GraphNode curNode : p.getGraphNodes()) {
            neverReturnNodes.add(curNode);
        }
        for (GraphNode curNeighbor : neighbors) {
            if (!neverReturnNodes.contains(curNeighbor)) {
                if (!p.getGraphNodes().contains(curNeighbor)) {
                    Path pNew = p.clone();
                    pNew.addNode(curNeighbor);
                    pNew.updateCost(Heuristic.compareTo(curNeighbor));
                    legalPaths.add(pNew);
                } 
            }
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
