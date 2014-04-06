package ShortCircuit.DataStructures;

/**
 * TODO: Write description for STCNode
 * @author clarence
 */
public class STCNode<E>{
    public E element;
    public STCNode<E> bottom;
    
    public STCNode(E _element) {
        element = _element;
    }
    
    public E getElement() {
        return element;
    }
    
    public void setBottom(STCNode<E> _bottom) {
        bottom = _bottom;
    }
    public STCNode<E> getBottom() {
        return bottom;
    }
    public void nullify() {
        element = null;
    }
    
}
