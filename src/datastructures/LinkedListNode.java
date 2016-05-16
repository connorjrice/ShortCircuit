package datastructures;


public class LinkedListNode<T> {

    private T element;
    private LinkedListNode<T> neighbor;

    public LinkedListNode(T _element) {
        element = _element;
        neighbor = null;
    }

    public void setNeighbor(LinkedListNode<T> newNeighbor) {
        neighbor = newNeighbor;
    }
 
    public LinkedListNode<T> getNeighbor() {
        return neighbor;
    }

    public T getElement() {
        return element;
    }

    @Override
    public String toString() {
        return element.toString();
    }
   

}







