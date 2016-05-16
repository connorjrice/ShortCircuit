package sc.pathfinding;

import datastructures.nodes.GraphNode;


/**
 *
 * @author Connor Rice
 */
public class jMEHeuristic implements Heuristic {
    
    private float[] endPosition;
    
    public jMEHeuristic() {
        
    }

    @Override
    public float compareTo(GraphNode n) {
        float[] curCoords = n.getCoordArray();
        float distx = Math.abs(endPosition[0] - curCoords[0]);
        float disty = Math.abs(endPosition[1] - curCoords[1]);
        return distx + disty;
    }

    @Override
    public void setEndPosition(Object compPos) {
        String posString = (String) compPos;
        String[] endString = posString.split(",");
        float[] endPos = new float[endString.length];
        for (int i = 0; i < endString.length; i++) {
            endPos[i] = Float.parseFloat(endString[i]);
        }
        this.endPosition = endPos;
    }
    
    public float[] getEndPosition() {
        return this.endPosition;
    }    


    
}
