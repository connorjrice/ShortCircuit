package datastructures;

import datastructures.nodes.GraphNode;
import java.util.HashMap;

/**
 * Implementation of an Unweighted Graph datastructure
 * @author Connor Rice
 * @param <T>
 */
public class UnweightedGraph<T extends Comparable> {

    private boolean[][] edges;
    private GraphNode[] nodes;
    private HashMap nodeHash;
    private int maxSize;
    private int currentSize;

    public UnweightedGraph(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        this.edges = new boolean[maxSize][maxSize];
        this.nodes = new GraphNode[maxSize];
        this.nodeHash = new HashMap(maxSize);
    }

    public void addNode(Comparable element) { // set in condition for if last
        GraphNode<T> newNode = new GraphNode<T>(currentSize, element);
        if (currentSize < maxSize - 1) {
            nodes[currentSize] = newNode;
            nodeHash.put(element, newNode);
            currentSize++;
        } else {
            doubleCapacity(newNode);
        }
    }

    public int getIndex(Comparable element) {
        return ((GraphNode) nodeHash.get(element)).getIndex();
    }

    public Comparable getElement(int index) {
        return nodes[index].getElement();
    }

    public GraphNode<T> getNode(int index) {
        return nodes[index];
    }

    public GraphNode<T> getNode(Comparable element) {
        return (GraphNode) nodeHash.get(element);
    }

    public void addEdge(Comparable firstElement, Comparable secondElement) {
        addEdge(getNode(firstElement), getNode(secondElement));
    }

    public void addEdge(GraphNode<T> firstNode, GraphNode<T> secondNode) {
        if (firstNode != null && secondNode != null && firstNode.getIndex() != secondNode.getIndex()) {
            edges[firstNode.getIndex()][secondNode.getIndex()] = true;
            edges[secondNode.getIndex()][firstNode.getIndex()] = true;
        }
    }

    public void removeEdges(GraphNode<T> doomedNode) {
        int doomedIndex = doomedNode.getIndex();
        for (int i = 0; i < edges.length; i++) {
            edges[doomedIndex][i] = false;
        }
    }

    public void removeEdge(GraphNode<T> firstNode, GraphNode<T> secondNode) {
        if (firstNode.getIndex() >= 0 && firstNode.getIndex() < currentSize) {
            if (secondNode.getIndex() >= 0 && firstNode.getIndex() < currentSize) {
                edges[firstNode.getIndex()][secondNode.getIndex()] = false;
                edges[secondNode.getIndex()][firstNode.getIndex()] = false;
            }
        }

    }

    public boolean isEdge(int i, int j) {
        return edges[i][j];
    }

    public int[] getNeighbors(int index) {
        int[] neighbors = new int[9];
        int arrayIndex = 0;
        for (int i = 0; i < currentSize; i++) {
            if (edges[index][i]) {
                neighbors[arrayIndex] = i;
                arrayIndex++;
            }
        }
        return neighbors;
    }

    /**
     * * Breadth and Depth Traversals **
     */
    public GraphNode<T> breadthFirstTraversal(Comparable element) {
        return breadthFirstTraversal(new GraphNode<T>(element));
    }

    public GraphNode<T> breadthFirstTraversal(GraphNode<T> snode) {
        Queue<GraphNode> queue = new Queue<GraphNode>();
        boolean[] marked = new boolean[currentSize];
        queue.push(nodes[0]);
        while (!queue.isEmpty()) {
            GraphNode node = queue.pop();
            if (!marked[node.getIndex()]) {
                marked[node.getIndex()] = true;
            }
            if (node.getElement().equals(snode.getElement())) {
                return node;
            }
            int[] neighbors = getNeighbors(node.getIndex());
            int arrayIndex = 0;
            while (neighbors[arrayIndex] != 0) {
                queue.push(getNode(neighbors[arrayIndex]));
            }
        }
        return null;
    }

    public GraphNode<T> depthFirstTraversal(Comparable element) {
        return depthFirstTraversal(new GraphNode<T>(element));
    }

    public GraphNode<T> depthFirstTraversal(GraphNode<T> snode) {
        Stack<GraphNode> stack = new Stack<GraphNode>();
        boolean[] marked = new boolean[currentSize];
        stack.push(nodes[0]);
        while (!stack.isEmpty()) {
            GraphNode node = stack.pop();
            if (!marked[node.getIndex()]) {
                marked[node.getIndex()] = true;
            }
            if (node.getElement().equals(snode.getElement())) {
                return node;
            }

            int[] neighbors = getNeighbors(node.getIndex());
            int arrayIndex = 0;
            while (neighbors[arrayIndex] != 0) {
                stack.push(getNode(neighbors[arrayIndex]));
            }
        }
        return null;
    }

    public void printNodes() {
        for (GraphNode curNode : nodes) {
            if (curNode != null) {
                System.out.println(curNode.getElement());
            }
        }
    }

    /**
     * * Array doubling methods **
     */
    private void doubleCapacity(GraphNode<T> newNode) {
        maxSize *= 2;
        nodes = doubleNodes(newNode);
        edges = doubleEdges();

    }

    private GraphNode[] doubleNodes(GraphNode<T> newNode) {
        nodes[currentSize] = newNode;
        nodeHash.put(newNode.getElement(), newNode);
        currentSize++;
        GraphNode[] newNodes = new GraphNode[maxSize];
        for (GraphNode<T> curNode : nodes) {
            newNodes[curNode.getIndex()] = curNode;
        }
        return newNodes;
    }

    private boolean[][] doubleEdges() {
        boolean[][] newEdges = new boolean[maxSize][maxSize];
        int i = 0;
        int j = 0;
        for (boolean[] curbool : edges) {
            i++;
            for (boolean bool : curbool) {
                newEdges[i][j] = bool;
                j++;
            }
            j = 0;
        }
        return newEdges;
    }

    public String[] getElementStrings() {
        String[] str = new String[currentSize];
        for (int i = 0; i < currentSize; i++) {
            str[i] = nodes[i].getElement().toString();
        }
        return str;
    }
}