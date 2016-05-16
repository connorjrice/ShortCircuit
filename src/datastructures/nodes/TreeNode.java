package datastructures.nodes;

public class TreeNode<T extends Comparable> {

    private TreeNode<T> leftChild;
    private TreeNode<T> rightChild;
    private Comparable data;

    public TreeNode() {
    }

    public TreeNode(Comparable obj) {
        data = obj;
        leftChild = null;
        rightChild = null;
    }

    public void addNode(TreeNode<T> newNode) {
        int comp = newNode.getData().compareTo(data);
        if (comp < 0) {
            if (hasLeftChild()) {
                getLeftChild().addNode(newNode);
            } else {
                setLeftChild(newNode);
            }
        } else if (comp > 0) {
            if (hasRightChild()) {
                getRightChild().addNode(newNode);
            } else {
                setRightChild(newNode);
            }
        }
    }

    public void printNodes() {
        if (hasLeftChild()) {
            getLeftChild().printNodes();
        }
        System.out.println(getData() + " ");
        if (hasRightChild()) {
            getRightChild().printNodes();
        }
    }

    public void setLeftChild(TreeNode<T> newChild) {
        leftChild = newChild;
    }

    public void setRightChild(TreeNode<T> newChild) {
        rightChild = newChild;
    }

    public Comparable getData() {
        return data;
    }

    public void setData(Comparable obj) {
        data = obj;
    }

    public TreeNode<T> getLeftChild() {
        return leftChild;
    }

    public TreeNode<T> getRightChild() {
        return rightChild;
    }

    public boolean hasRightChild() {
        return rightChild != null;
    }

    public boolean hasLeftChild() {
        return leftChild != null;
    }

    public boolean hasChildren() {
        return rightChild != null || leftChild != null;
    }

    public boolean hasOneChild() {
        return rightChild != null ^ leftChild != null;
    }
}