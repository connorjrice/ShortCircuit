package ShortCircuit.PathFinding;

import ShortCircuit.DataStructures.Graph;
import ShortCircuit.DataStructures.Interfaces.EdgeBuilder;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;


    
/**
 *
 * @author Connor Rice
 */
public class JMEEdgeBuilder implements EdgeBuilder {
    private Graph worldGraph;
    private Node rootNode;
    private final float precision;
    
    public JMEEdgeBuilder(Graph worldGraph, Node rootNode, float precision) {
        this.worldGraph = worldGraph;
        this.rootNode = rootNode;
        this.precision = precision;
    }

    public void addEdges() {
        String[] elements = worldGraph.getElementStrings();
        for (String s : elements) {
            addEdges(s);
        }
    }

    private void addEdges(String targetName) {
        String[] tn = targetName.split(",");
        float[] startPos = new float[tn.length];
        for (int i = 0; i < tn.length; i++) {
            startPos[i] = Float.parseFloat(tn[i]);
        }
        for (float i = startPos[0] - precision; i <= startPos[0] + precision; i += precision) {
            for (float j = startPos[1] - precision; j <= startPos[1] + precision; j += precision) {
                String is = Float.toString(i);
                String js = Float.toString(j);
                String nodeName = is + "," + js;
                worldGraph.addEdge(targetName, nodeName);

            }
        }
    }

    
}