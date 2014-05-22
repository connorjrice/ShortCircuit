package ShortCircuit.DataStructures;

import ShortCircuit.DataStructures.Nodes.QueueNode;

public class Queue<T> {

    private int size; // Size of the Queue
    private QueueNode<T> front; // Node at front of Queue
    private QueueNode<T> back;

    public Queue() {
        this.size = 0;
        this.front = null;
    }

    public void clear() {
        front = null;
        back = null;
        size = 0;
    }

    public void enqueue(T newElement) {
        QueueNode<T> queueIn = new QueueNode<T>(newElement);
        if (front == null) {
            front = queueIn;
            back = queueIn;
            size++;
        } else {
            back.setNeighbor(queueIn);
            back = queueIn;
            size++;
        }

    }

    public T dequeue() {
        T element;
        if (back == front) {
            size--;
            element = front.getElement();
            front = null;
            back = null;
        } else {
            size--;
            element = front.getElement();
            front = front.getNeighbor();
        }
        return element;
    }

    public T peek() {
        return front.getElement();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
