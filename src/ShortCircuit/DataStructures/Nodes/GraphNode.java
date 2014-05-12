package ShortCircuit.DataStructures.Nodes;

public class GraphNode<T> {

    private Comparable element;
    private int index;

    public GraphNode(int _index, Comparable newElement) {
	index = _index;
	element = newElement;
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
    
    public float[] getCoordArray() {
        String[] strArr = ((String) element).split(",");
        float[] coords = new float[strArr.length];
        for (int i= 0; i < strArr.length; i++) {
            coords[i] = Float.parseFloat(strArr[i]);
        }
        return coords;
    }
    

}