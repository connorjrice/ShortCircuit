package ShortCircuit.DataStructures.Heuristics;

import ShortCircuit.DataStructures.Interfaces.Heuristic;
import ShortCircuit.DataStructures.Nodes.GraphNode;


/**
 *
 * @author Connor Rice
 */
public class jMEHeuristic implements Heuristic {
    
    private float[] endPosition;
    
    public jMEHeuristic() {
        
    }

    public float compareTo(Object n) {
        GraphNode node = (GraphNode) n;
        String elementString = (String) node.getElement();
        float[] curCoords = new float[elementString.length()];
        String[] coordsString = elementString.split(",");
        for (int i = 0; i < coordsString.length; i++) {
            curCoords[i] = Float.parseFloat(coordsString[i]);
        }
        float distx = Math.abs(endPosition[0] - curCoords[0]);
        float disty = Math.abs(endPosition[1] - curCoords[1]);
        //System.out.println("Distx: " + Float.toString(distx));
        //System.out.println("Disty: " + Float.toString(disty));
        return distx + disty;
    }

    public void setEndPosition(Object compPos) {
        String posString = (String) compPos;
        String[] endString = posString.split(",");
        float[] endPos = new float[endString.length];
        for (int i = 0; i < endString.length; i++) {
            endPos[i] = Float.parseFloat(endString[i]);
        }
        System.out.println(endPosition);
        this.endPosition = endPos;
    }
    
    public float[] getEndPosition() {
        return this.endPosition;
    }    


    
}
