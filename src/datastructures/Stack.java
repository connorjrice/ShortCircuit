package datastructures;

import datastructures.nodes.StackNode;

public class Stack<T> {

    private int size; // Size of the stack
    private StackNode<T> top; // Node on top of stack

    public Stack() {
        this.size = 0;
        this.top = null;
    }

    public void push(T newTopElement) {
        StackNode<T> newTopNode = new StackNode<>(newTopElement);
        if (top == null) {
            top = newTopNode;
        } else {
            newTopNode.setAbove(top);
            top = newTopNode;
        }
        size++;
    }

    public T pop() {
        T element = top.getElement();
        top = top.getNeighbor();
        return element;
    }

    public T peek() {
        return top.getElement();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}