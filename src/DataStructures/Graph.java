package DataStructures;

import DataStructures.Nodes.GraphNode;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of an Unweighted Graph datastructure with A* Pathfinding.
 *
 * @author Connor Rice
 * @param 
 */
public class Graph<T extends Comparable> implements Savable{

    private boolean[][] edges;
    private GraphNode[] nodes;
    private Map nodeHash;
    private int maxSize;
    private int currentSize;

    public Graph() {
        this.nodeHash = new HashMap(maxSize);
    }
    
    public Graph(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        this.edges = new boolean[maxSize][maxSize];
        this.nodes = new GraphNode[maxSize];
        this.nodeHash = new HashMap(maxSize);
    }

    public void addNode(String element) { // set in condition for if last
        GraphNode newNode = new GraphNode(currentSize, element);
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

    public GraphNode getNode(int index) {
        return nodes[index];
    }

    public GraphNode getNode(Comparable element) {
        return (GraphNode) nodeHash.get(element);
    }

    public void addEdge(Comparable firstElement, Comparable secondElement) {
        addEdge(getNode(firstElement), getNode(secondElement));
    }

    public void addEdge(GraphNode firstNode, GraphNode secondNode) {
        if (firstNode != null && secondNode != null && firstNode.getIndex() != secondNode.getIndex()) {
            edges[firstNode.getIndex()][secondNode.getIndex()] = true;
            edges[secondNode.getIndex()][firstNode.getIndex()] = true;
        }
    }

    public void removeEdges(GraphNode doomedNode) {
        int doomedIndex = doomedNode.getIndex();
        for (int i = 0; i < edges.length; i++) {
            edges[doomedIndex][i] = false;
        }
    }

    public void removeEdge(GraphNode firstNode, GraphNode secondNode) {
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
    public GraphNode breadthFirstTraversal(String element) {
        return breadthFirstTraversal(new GraphNode(element));
    }

    public GraphNode breadthFirstTraversal(GraphNode snode) {
        Queue<GraphNode> queue = new Queue<GraphNode>();
        boolean[] marked = new boolean[currentSize];
        queue.enqueue(nodes[0]);
        while (!queue.isEmpty()) {
            GraphNode node = queue.dequeue();
            if (!marked[node.getIndex()]) {
                marked[node.getIndex()] = true;
            }
            if (node.getElement().equals(snode.getElement())) {
                return node;
            }
            int[] neighbors = getNeighbors(node.getIndex());
            int arrayIndex = 0;
            while (neighbors[arrayIndex] != 0) {
                queue.enqueue(getNode(neighbors[arrayIndex]));
            }
        }
        return null;
    }

    public GraphNode depthFirstTraversal(String element) {
        return depthFirstTraversal(new GraphNode(element));
    }

    public GraphNode depthFirstTraversal(GraphNode snode) {
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
    private void doubleCapacity(GraphNode newNode) {
        maxSize *= 2;
        nodes = doubleNodes(newNode);
        edges = doubleEdges();

    }

    private GraphNode[] doubleNodes(GraphNode newNode) {
        nodes[currentSize] = newNode;
        nodeHash.put(newNode.getElement(), newNode);
        currentSize++;
        GraphNode[] newNodes = new GraphNode[maxSize];
        for (GraphNode curNode : nodes) {
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
    
    public int getSize() {
        return currentSize;
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
        int[] intParams = in.readIntArray("intParams", new int[2]);
        currentSize = intParams[0];
        maxSize = intParams[1];
        edges = in.readBooleanArray2D("edges", new boolean[maxSize][maxSize]);
        ArrayList<GraphNode> nodeA =  in.readSavableArrayList("nodeA", new ArrayList<GraphNode>());
        nodes = new GraphNode[maxSize];
        System.out.println(nodes.length);
        for (int i = 0; i < nodeA.size(); i++) {
            nodes[i] = nodeA.get(i);
        }
        nodeHash = in.readStringSavableMap("nodeHash", new HashMap());
     
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
        int[] intParams = new int[] {currentSize, maxSize};
        out.write(intParams, "intParams", new int[2]);
        out.write(edges, "edges", new boolean[currentSize][currentSize]);
        ArrayList<GraphNode> nodeA = new ArrayList(Arrays.asList(nodes));
        out.writeStringSavableMap(nodeHash, "nodeHash", new HashMap());
        out.writeSavableArrayList(nodeA, "nodeA", new ArrayList<GraphNode>());
        
    }

}