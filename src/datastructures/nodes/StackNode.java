package datastructures.nodes;

public class StackNode<T> {

    T element;
    StackNode<T> neighbor;

    public StackNode(T _element) {
        element = _element;
        neighbor = null;
    }

    public void setAbove(StackNode<T> newNeighbor) {
        neighbor = newNeighbor;
    }

    public StackNode<T> getNeighbor() {
        return neighbor;
    }

    public T getElement() {
        return element;
    }
}
