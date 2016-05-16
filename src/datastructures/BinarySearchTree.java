package datastructures;

import datastructures.nodes.TreeNode;

/**
 * @author Connor Rice
 */
public class BinarySearchTree<T extends Comparable> {

    private TreeNode<T> root;

    public BinarySearchTree() {
        root = null;
    }
    
    public void add(Comparable obj) {
        TreeNode<T> newNode = new TreeNode<T>();
        newNode.setData(obj);
        if (root == null) {
            root = newNode;
        } else {
            root.addNode(newNode);
        }
    }

    /**
     * Returns true if the input object is contained within the tree.
     * @param obj
     * @return
     */
    public boolean contains(Comparable obj) {
        TreeNode<T> current = root;
        while (current != null) {
            int d = current.getData().compareTo(obj);
            if (d == 0) {
                return true;
            } else if (d > 0) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        return false;
    }

    public void remove(Comparable obj) {
        TreeNode<T> doomedNode = root;
        TreeNode<T> parent = null;
        boolean found = false;
        while (!found && doomedNode != null) {
            int d = doomedNode.getData().compareTo(obj);
            if (d == 0) {
                found = true;
            } else {
                parent = doomedNode;
                if (d > 0) {
                    doomedNode = doomedNode.getLeftChild();
                } else {
                    doomedNode = doomedNode.getRightChild();
                }
            }
        }

        if (!found) {
            return;
        } // Object was not found.

        if (doomedNode.getLeftChild() == null
                || doomedNode.getRightChild() == null) {
            TreeNode<T> newNode;
            if (doomedNode.getLeftChild() == null) {
                newNode = doomedNode.getRightChild();
            } else {
                newNode = doomedNode.getLeftChild();
            }
            if (parent == null) { // found in root
                root = newNode;
            } else if (parent.getLeftChild() == doomedNode) {
                parent.setLeftChild(newNode);
            } else {
                parent.setRightChild(newNode);
            }
            return;
        }

        // Neither subtree is empty, so we need to continue down

        // Find smallest element of right subtree

        TreeNode<T> smallestParent = doomedNode;
        TreeNode<T> smallest = doomedNode.getRightChild();
        while (smallest.getLeftChild() != null) {
            smallestParent = smallest;
            smallest = smallest.getLeftChild();
        }

        // smallest now contains the smallest child in the right subtree

        // Move contents of smallest, unlink it's child

        doomedNode.setData(smallest.getData());
        if (smallestParent.equals(doomedNode)) {
            smallestParent.setRightChild(smallest.getRightChild());
        } else {
            smallestParent.setLeftChild(smallest.getRightChild());
        }
    }

    public TreeNode getRoot() {
        return root;
    }
    
    public int getMaxDepth() {
        return depthHelper(root);
    }
    
    private int depthHelper(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            return Math.max(depthHelper(node.getLeftChild()), 
                    depthHelper(node.getRightChild())) + 1;
        }
    }
    
    public void print() {
        if (root != null) {
            root.printNodes();
        }
    }
}
