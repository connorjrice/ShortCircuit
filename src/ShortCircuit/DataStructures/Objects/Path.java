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
    private boolean isIncomplete;
    private boolean marked;
    
    public Path(ArrayList<GraphNode> pathNodes) {
        this.pathNodes = pathNodes;
        curIndex = 0;
        isIncomplete = true;
        marked = false;
    }
    
    public ArrayList<GraphNode> getGraphNodes() {
        return pathNodes;
    }
    
    public GraphNode getLastNode() {
        return pathNodes.get(pathNodes.size()-1);
    }
    
    public void addNode(GraphNode newNode) {
        pathNodes.add(newNode);
    }
    
    public void updateCost(float estCost) {
        //cost = estCost;
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
    
    public void resetIndex() {
        curIndex = 0;
    }
   
    public GraphNode get(int index) {
        return pathNodes.get(index);
    }
    
    public GraphNode peekNextPathNode() {
        if (curIndex+1 < pathNodes.size()) {
            return pathNodes.get(curIndex+1);
        }
        else {
            return null;
        }
    }
    
    public boolean isIncomplete() {
        return isIncomplete;
    }
    
    public void setComplete() {
        isIncomplete = false;
    }
    
    public void setIncomplete() {
        isIncomplete = true;
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
    
    public boolean contains(GraphNode pn) {
        return (pathNodes.contains(pn));
    }
    
}
