package DataStructures.Nodes;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import java.io.IOException;

public class GraphNode implements Savable {

    private String element;
    private int index;
    private float[] coords;
    
    public GraphNode() {
        
    }

    public GraphNode(int _index, String newElement) {
        index = _index;
        element = newElement;
        setCoordArray();
    }

    public GraphNode(String newElement) {
        index = -1;
        element = newElement;
    }

    public int getIndex() {
        return index;
    }

    public String getElement() {
        return element;
    }

    private void setCoordArray() {
        String[] strArr = ((String) element).split(",");
        coords = new float[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            coords[i] = Float.parseFloat(strArr[i]);
        }
    }

    public Vector3f getVector3f() {
        String[] strArr = ((String) element).split(",");
        return new Vector3f(Float.parseFloat(strArr[0]), Float.parseFloat(strArr[1]), 0.1f);
    }

    public float[] getCoordArray() {
        return coords;
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
        element = in.readString("element", "");
        index = in.readInt("index", 0);
        coords = in.readFloatArray("coords", new float[3]);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        out.write(element, "element", "");
        out.write(index, "index", 0);
        out.write(coords, "coords", new float[coords.length]);
    }

}