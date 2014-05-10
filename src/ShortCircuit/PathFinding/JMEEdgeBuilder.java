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
    
    public JMEEdgeBuilder(Graph worldGraph, Node rootNode) {
        this.worldGraph = worldGraph;
        this.rootNode = rootNode;
    }

    public void addEdges() {
        String[] elements = worldGraph.getElementStrings();
        for (String s : elements) {
            addEdges(s);
        }
    }

    private void addEdges(String targetName) {
        String[] tn = targetName.split(",");
        int[] startPos = new int[tn.length];
        for (int i = 0; i < tn.length; i++) {
            startPos[i] = Integer.parseInt(tn[i]);
        }
        for (int i = startPos[0] - 2; i <= startPos[0] + 2; i += 2) {
            for (int j = startPos[1] - 2; j <= startPos[1] + 2; j += 2) {
                String is = Integer.toString(i);
                String js = Integer.toString(j);
                String nodeName = is + "," + js;
                Spatial newNode = rootNode.getChild(nodeName);
                Spatial targetNode = rootNode.getChild(targetName);
                if (newNode != null && targetNode != null) {
                    if (newNode.getUserData("Name").equals("Unblocked")) {
                        if (targetNode.getUserData("Name").equals("Unblocked")) {
                            worldGraph.addEdge(targetName, nodeName);
                        }
                    }
                }
            }
        }
    }

    
}
