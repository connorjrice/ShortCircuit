package ShortCircuit.PathFinding;

import DataStructures.Graph;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.scene.Node;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 *
 * @author Connor Rice
 */
public class JEdgeManipulator implements Savable{

    private Graph worldGraph;
    private float precision;
    private DecimalFormat numFormatter;
    private String[] blockedNodes;
    
    public JEdgeManipulator() {
        this.numFormatter = new DecimalFormat("0.0");
    }

    public JEdgeManipulator(Graph worldGraph, Node targetNode, String[] geomHash, float precision) {
        this.worldGraph = worldGraph;
        this.precision = precision;
        this.numFormatter = new DecimalFormat("0.0");
        this.blockedNodes = geomHash;
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
    
    public void writeToNode(Node n) {
        n.setUserData("Edges", this);
    }

    private void addEdge(String nodeName, String targetName) {
        if (!nodeName.equals(targetName)) {
            boolean possible = true;
            for (int i = 0; i < blockedNodes.length; i++) {
                if (blockedNodes[i].equals(nodeName) || blockedNodes[i].equals(targetName)) {
                    possible = false;
                }
            }
            if (possible) {
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
    
    public Graph getWorldGraph() {
        return worldGraph;
    }

    
        @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
        worldGraph = (Graph) in.readSavable("worldGraph", new Graph());
        precision = in.readFloat("precision", 1.0f);
        blockedNodes = in.readStringArray("blockedNodes", new String[20]);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.write(worldGraph, "worldGraph", new Graph());
        out.write(precision, "precision", 1.0f);
        out.write(blockedNodes, "blockedNodes", new String[blockedNodes.length]);

    }

}
