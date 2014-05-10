package ShortCircuit.DataStructures.Interfaces;


import ShortCircuit.DataStructures.Nodes.GraphNode;
import ShortCircuit.DataStructures.Objects.Path;
import java.util.ArrayList;

/**
 *
 * @author Development
 */
public interface PathFinder {
    
    ArrayList<Path> frontier = new ArrayList<Path>();
    ArrayList<Path> closed = new ArrayList<Path>();
    
    public Path pathFind(Path curPath);
    
    public ArrayList<Path> getLegalPaths(Path curPath);
    
    public Path getNextPath();
    
    public Path clonePath(Path p);
    
    public void setEndNode(GraphNode endNode);
    
    public GraphNode getEndNode();
    
    public Path initPathFinder(GraphNode start, GraphNode end);
        

    
}
