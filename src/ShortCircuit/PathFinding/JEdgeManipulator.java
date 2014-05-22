package ShortCircuit.PathFinding;

import DataStructures.Graph;
import com.jme3.scene.Node;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 *
 * @author Connor Rice
 */
public class JEdgeManipulator implements EdgeManipulator {

    private Graph worldGraph;
    private final float precision;
    private Node rootNode;
    private DecimalFormat numFormatter;
    private HashMap geomHash;
    private final String UNBLOCKED = "Unblocked";

    public JEdgeManipulator(Graph worldGraph, Node targetNode, HashMap geomHash, float precision) {
        this.worldGraph = worldGraph;
        this.precision = precision;
        //this.rootNode = targetNode;
        this.numFormatter = new DecimalFormat("0.0");
        this.geomHash = geomHash;
    }

    public void addInitialEdges() {
        String[] elements = worldGraph.getElementStrings();
        for (String s : elements) {
            add4Edge(s);
        }
    }

    private void add4Edge(String targetName) {
        String[] tn = targetName.split(",");
        float[] startPos = new float[tn.length];
        for (int i = 0; i < tn.length; i++) {
            startPos[i] = Float.parseFloat(tn[i]);
        }
        for (float y = startPos[0] - precision; y < startPos[0] + precision; y += precision) {
            String is = formatRoundNumber(y);
            String nodeName = is + "," + startPos[1];
            addEdge(nodeName, targetName);
        }
        for (float x = startPos[1] - precision; x < startPos[1] + precision; x += precision) {
            String is = formatRoundNumber(x);
            String nodeName = startPos[0] + "," + x;
            addEdge(nodeName, targetName);

        }
    }

    private void addEdge(String nodeName, String targetName) {
        if (!nodeName.equals(targetName)) {
            if (geomHash.get(nodeName) == null && geomHash.get(targetName) == null) {
                worldGraph.addEdge(targetName, nodeName);
            }
        }

    }
    
    public void remove4Edge(String target) {
        worldGraph.removeEdges(worldGraph.getNode(target));
    }
    
    private float[] parseString(String in) {
        String[] tn = in.split(",");
        float[] startPos = new float[tn.length];
        for (int i = 0; i < tn.length; i++) {
            startPos[i] = Float.parseFloat(tn[i]);
        }
        return startPos;
    }

    private String formatRoundNumber(Float value) {
        return numFormatter.format(Math.round(value));
    }
}
