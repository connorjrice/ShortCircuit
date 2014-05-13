package ShortCircuit.DataStructures.Nodes;

import com.jme3.math.Vector3f;

public class GraphNode<T> {

    private Comparable element;
    private int index;
    private float[] coords;
    
    public GraphNode(int _index, Comparable newElement) {
	index = _index;
	element = newElement;
        setCoordArray();
    }
    
    public GraphNode(Comparable newElement) {
	index = -1;
	element = newElement;
    }

    public int getIndex() {
	return index;
    }
    
    public Comparable getElement() {
	return element;
    }
    
    private void setCoordArray() {
        String[] strArr = ((String) element).split(",");
        coords = new float[strArr.length];
        for (int i= 0; i < strArr.length; i++) {
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
    

}