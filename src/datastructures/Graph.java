package datastructures;

import datastructures.nodes.GraphNode;
import java.util.HashMap;

/**
 * Implementation of a weighted Graph data structure
 * @author Connor Rice
 * @param <T>
 */
public class Graph<T extends Comparable> {

    private int[][] edges;
    private GraphNode[] nodes;
    private final HashMap nodeHash;
    private int maxSize;
    private int currentSize;

    public Graph(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        this.edges = new int[maxSize][maxSize];
        this.nodes = new GraphNode[maxSize];
        this.nodeHash = new HashMap(maxSize);
    }
    
    public void createNodes() {
        for (int i = 0; i < maxSize; i++) {
            nodes[i] = new GraphNode(i,i);
            currentSize++;
        }
    }

    public void addNode(Comparable element) { // set in condition for if last
        GraphNode<T> newNode = new GraphNode<>(currentSize, element);
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

    public void addEdge(Comparable firstElement, Comparable secondElement, int w) {
        addEdge(getNode(firstElement), getNode(secondElement), w);
    }

    public void addEdge(GraphNode<T> firstNode, GraphNode<T> secondNode, int w) {
        if (firstNode != null && secondNode != null && firstNode.getIndex() != secondNode.getIndex()) {
            edges[firstNode.getIndex()][secondNode.getIndex()] = w;
            //System.out.println(edges[firstNode.getIndex()][secondNode.getIndex()]);
        }
    }

    public void removeEdges(GraphNode<T> doomedNode) {
        int doomedIndex = doomedNode.getIndex();
        for (int i = 0; i < edges.length; i++) {
            edges[doomedIndex][i] = 0;
        }
    }

    public void removeEdge(GraphNode<T> firstNode, GraphNode<T> secondNode) {
        if (firstNode.getIndex() >= 0 && firstNode.getIndex() < currentSize) {
            if (secondNode.getIndex() >= 0 && firstNode.getIndex() < currentSize) {
                edges[firstNode.getIndex()][secondNode.getIndex()] = 0;
                edges[secondNode.getIndex()][firstNode.getIndex()] = 0;
            }
        }
    }

    public int[][] getEdges() {
        return edges;
    }
    
    public int isEdge(int i, int j) {
        return edges[i][j];
    }

    public int[] getNeighbors(int index) {
        int[] neighbors = new int[3];
        int arrayIndex = 0;
        for (int i = 0; i < currentSize; i++) {
            if (edges[index][i] > 0) {
                neighbors[arrayIndex] = i;
                arrayIndex++;
            }
        }
        return neighbors;
    }
    
    

    /**
     * * Breadth and Depth Traversals **
     */
    
    /**
     * Perform a breadth-first search and return the boolean[] of visited nodes.
     * @return 
     */
    public boolean[] getTraversal() {
        Queue<GraphNode> queue = new Queue<>();
        boolean[] seen = new boolean[currentSize];
        queue.push(nodes[0]);
        while (!queue.isEmpty()) {
            GraphNode node = queue.pop();
            for (int i = 0; i < currentSize; i++) {
                if (edges[node.getIndex()][i] >= 0 && !seen[i]) {
                    seen[i] = true;
                    queue.push(nodes[i]);
                }
            }            
        }
        return seen;        
    }
    
    /**
     * Returns a graphnode that contains element.
     * @param element
     * @return 
     */
    public GraphNode<T> breadthFirstTraversal(Comparable element) {
        return breadthFirstTraversal(new GraphNode<>(element));
    }

    public GraphNode<T> breadthFirstTraversal(GraphNode<T> snode) {
        Queue<GraphNode> queue = new Queue<>();
        boolean[] seen = new boolean[currentSize];
        queue.push(nodes[0]);
        while (!queue.isEmpty()) {
            GraphNode node = queue.pop();
            if (!seen[node.getIndex()]) {
                seen[node.getIndex()] = true;
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
        return depthFirstTraversal(new GraphNode<>(element));
    }

    public GraphNode<T> depthFirstTraversal(GraphNode<T> snode) {
        Stack<GraphNode> stack = new Stack<>();
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

    private int[][] doubleEdges() {
        int[][] newEdges = new int[maxSize][maxSize];
        int i = 0;
        int j = 0;
        for (int[] e : edges) {
            
            i++;
            for (int e2 : e) {
                newEdges[i][j] = e2;
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