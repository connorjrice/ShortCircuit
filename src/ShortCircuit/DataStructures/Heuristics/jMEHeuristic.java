package ShortCircuit.DataStructures.Heuristics;

import ShortCircuit.DataStructures.Interfaces.Heuristic;
import ShortCircuit.DataStructures.Nodes.GraphNode;


/**
 *
 * @author Connor Rice
 */
public class jMEHeuristic implements Heuristic {
    
    private int[] endPosition;
    
    public jMEHeuristic() {
        
    }

    public int compareTo(Object n) {
        GraphNode node = (GraphNode) n;
        String elementString = (String) node.getElement();
        int[] curCoords = new int[elementString.length()];
        String[] coordsString = elementString.split(",");
        for (int i = 0; i < coordsString.length; i++) {
            curCoords[i] = Integer.parseInt(coordsString[i]);
        }
        int distx = endPosition[0] - curCoords[0];
        int disty = endPosition[1] - curCoords[1];
        return distx + disty;
    }

    public void setEndPosition(Object compPos) {
        String posString = (String) compPos;
        String[] endString = posString.split(",");
        int[] endPos = new int[endString.length];
        for (int i = 0; i < endString.length; i++) {
            endPos[i] = Integer.parseInt(endString[i]);
        }
        this.endPosition = endPos;
    }
    
    public int[] getEndPosition() {
        return this.endPosition;
    }    


    
}
