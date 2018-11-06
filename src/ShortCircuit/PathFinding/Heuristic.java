package ShortCircuit.PathFinding;

import DataStructures.Nodes.GraphNode;

/**
 * ASKMATTHEW: Generic primitive types
 * @author Development
 */
public interface Heuristic<T> {
    
    
    public void setEndPosition(T coords);

    public float compareTo(GraphNode n);
    

    
}
