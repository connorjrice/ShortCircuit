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
    

}