package ShortCircuit.DataStructures.Nodes;

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
    
    public float[] getCoordArray() {
        return coords;
    }
    

}