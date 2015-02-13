package datastructures;

import com.jme3.scene.Spatial;
import java.util.Comparator;

/**
 * Sorted Triple Coord Data Structure Custom data structure for Towers in
 * TowerDefense. Maximum size is 3, to reduce memory usage. Populates the STC
 * with the first three spatials that are offered, and then begins to compare
 * extra elements after size 3 has been reached. Comparisons are not essential,
 * as the structure is updated at a given time interval (which should be less
 * than 8 seconds.)
 *
 * @author Connor Rice
 */
public class STC<E extends Spatial> {

    protected STCNode<E> root;
    public int size = 0;
    private Comparator<E> creepComp;

    public STC(Comparator<E> comparator) {
        creepComp = comparator;
    }

    /**
     * Converts elements into nodes for the offering ceremony
     *
     * @param element - spatial that might be added to STC
     */
    public void offer(Spatial element) {
        offer(new STCNode(element));
    }

    /**
     * Checks to see if there are 3 elements
     *
     * @param newNode
     */
    public void offer(STCNode<E> newNode) {
        if (newNode != null) {
            if (root == null) {
                root = newNode;
                size += 1;
            } else if (root.getBottom() == null) {
                root.setBottom(newNode);
                size += 1;
            } else if (root.getBottom().getBottom() == null) {
                root.getBottom().setBottom(newNode);
                size += 1;
            } else {
                if (creepComp.compare(root.getElement(),
                        newNode.getElement()) == 1) {
                    newNode.setBottom(root);
                    root = newNode;
                } else if (creepComp.compare(root.getBottom().getElement(),
                        newNode.getElement()) == 1) {
                    newNode.setBottom(root.getBottom());
                    root.setBottom(newNode);
                } else if (creepComp.compare(root.getBottom().getBottom()
                        .getElement(), newNode.getElement()) == 1) {
                    root.getBottom().setBottom(newNode);
                }
            }
        }
    }

    /**
     * Sets the root to be the root's bottom node, decrements size.
     */
    public void remove() {
        root = root.getBottom();
        size -= 1;
    }

    /**
     * Returns the root node's element but does not remove it from the STC
     *
     * @return the root node's element
     */
    public Spatial peek() {
        return root.getElement();
    }

    /**
     * Empty out STC. Null root, size 0
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the size of the STC
     *
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the STC is empty. Returns false if size > 0.
     *
     * @return if list is empty or not
     */
    public boolean isEmpty() {
        if (size > 0) {
            return false;
        } else {
            return true;
        }
    }
}
