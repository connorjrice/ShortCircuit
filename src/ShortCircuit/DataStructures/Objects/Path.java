package ShortCircuit.DataStructures.Objects;


import ShortCircuit.DataStructures.Nodes.GraphNode;
import java.util.ArrayList;

/**
 * 
 * @author Connor Rice
 */
public class Path {
    
    private ArrayList<GraphNode> pathNodes;
    private float cost;
    private int curIndex;
    private boolean marked;
    
    public Path(ArrayList<GraphNode> pathNodes) {
        this.pathNodes = pathNodes;
        curIndex = 0;
        marked = false;
    }
    
    public ArrayList<GraphNode> getGraphNodes() {
        return pathNodes;
    }
    
    public GraphNode getLastNode() {
        return pathNodes.get(pathNodes.size()-1); // ASKMATTHEW: More efficient to keep internal tracker?
    }
    
    public void addNode(GraphNode newNode) {
        pathNodes.add(newNode);
    }
    
    // ASKMATTHEW: can we do a (possibly threaded) this every time we addNode?
    public void updateCost(float estCost) {
        cost = pathNodes.size() + estCost;
    }
    
    public float getCost() {
        return cost;
    }
    
    public boolean getEndReached() {
        return curIndex == pathNodes.size();
    }
    
    public GraphNode getNextPathNode() {
        GraphNode next = pathNodes.get(curIndex);
        curIndex++;
        return next;
    }
    
    public void setMarked() {
        marked = true;
    }
    
    public boolean getMarked() {
        return marked;
    }
    
    @Override
    public Path clone() {
        return new Path((ArrayList<GraphNode>) getGraphNodes().clone());
    }
    
}
