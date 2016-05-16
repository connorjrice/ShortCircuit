package datastructures.nodes;

public class QueueNode<T> {

    T element;
    QueueNode<T> neighbor;

    public QueueNode(T _element) {
        element = _element;
        neighbor = null;
    }

    public void setNeighbor(QueueNode<T> newNeighbor) {
        neighbor = newNeighbor;
    }

    public QueueNode<T> getNeighbor() {
        return neighbor;
    }

    public T getElement() {
        return element;
    }

    public String toString() {
        return element.toString();
    }
}
