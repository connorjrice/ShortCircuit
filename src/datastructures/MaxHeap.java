package datastructures;

/**
 * Implementation of a MaxHeap datastructure. It is based upon Cay Horstmann's
 * implementation in Big Java, but uses statically sized arrays instead of
 * ArrayLists, and adds more functionality.
 * @author Connor Rice, May 2014
 * @param <T>
 */
public class MaxHeap<T extends Comparable> {

    private Comparable<T>[] heap;
    private int maxSize;
    private int currentSize;
    private final int scalingFactor;

    /**
     * Constructor, takes in an integer that is the original size of the array
     * when the heap array is first instantiated.
     *
     * @param maxSize
     */
    public MaxHeap(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        this.scalingFactor = 2;
        this.heap = new Comparable[maxSize];
    }

    /**
     * Constructor with no input parameters. Creates a MinHeap of size 20
     */
    public MaxHeap() {
        this.maxSize = 20;
        this.currentSize = 0;
        this.scalingFactor = 2;
        this.heap = new Comparable[maxSize];
    }

    /**
     * Adds a new element to the MinHeap.
     *
     * @param newElement Comparable element to be added to the MinHeap.
     */
    public void add(Comparable newElement) {
        if (currentSize < maxSize - 1) {
            int index = currentSize;
            while (index > 0 && getParent(index).compareTo(newElement) > 0) {
                heap[index] = getParent(index);
                index = getParentIndex(index);
            }


            heap[index] = newElement;
            currentSize++;
        } else {
            doubleCapacity(newElement);
        }
    }

    /**
     * Returns the root of the heap without removing it from the heap.
     *
     * @return The root of the MinHeap, either smallest or largest element
     * depending on whether or not the MinHeap is a MinHeap or a MaxHeap.
     */
    public Comparable peek() {
        return heap[0];
    }

    /**
     * Returns the root of the heap and removes it from the heap.
     *
     * @return The root of the MinHeap, which is either the smallest element (if
     * the MinHeap is a MinHeap) or the largest element (if the MinHeap is a
     * MaxHeap.)
     */
    public Comparable remove() {
        Comparable minimum = heap[0];
        Comparable last = heap[currentSize - 1];
        if (currentSize > 0) {
            heap[0] = last;
            fixHeap();
        }
        heap[currentSize] = null;
        currentSize--;
        return minimum;
    }

    /**
     * Internal method to keep the structure of the MinHeap intact when removing
     * elements from the MinHeap.
     */
    private void fixHeap() {
        Comparable root = heap[0];
        int index = 0;
        boolean done = false;
        while (!done) {
            int childIndex = getLeftChildIndex(index);
            if (childIndex < getSize()) { // prevents IndexOutOfBoundsException
                Comparable child = getLeftChild(index);
                if (getRightChild(index) != null && getRightChild(index).compareTo(child) < 0) {
                    childIndex = getRightChildIndex(index);
                    child = getRightChild(index);
                }
                if (child != null && child.compareTo(root) < 0) {
                    heap[index] = child;
                    index = childIndex;
                } else {
                    done = true;
                }


            } else {
                done = true;
            }
            heap[index] = root;
        }
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private Comparable getLeftChild(int index) {
        return heap[getLeftChildIndex(index)];
    }

    private Comparable getRightChild(int index) {
        return heap[getRightChildIndex(index)];
    }

    private Comparable getParent(int index) {
        return heap[getParentIndex(index)];
    }

    /**
     * Returns the number of elements stored within the MinHeap.
     *
     * @return Current size of the MinHeap (number of elements stored within
     * MinHeap.)
     */
    public int getSize() {
        return currentSize;
    }

    /**
     * Returns whether or not the MinHeap is empty.
     *
     * @return True if MinHeap is empty. False if MinHeap is not empty.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Returns a copy of the internal MinHeap array.
     *
     * @return An array of comparable elements, sorted into a MinHeap.
     */
    public Comparable[] toArray() {
        return heap.clone();
    }

    /**
     * When trying to add an element to the heap when the heap's original size
     * has been filled, this method copies the contents of the original heap
     * into a new heap that is statically sized as maxSize * 2. Makes the
     * primitive arrays much more useful.
     *
     * @param newElement
     */
    private void doubleCapacity(Comparable newElement) {
        Comparable<T>[] newHeap = new Comparable[maxSize * scalingFactor];
        int i = 0;
        for (Comparable curComp : heap) {
            newHeap[i] = curComp;
            i++;
        }
        heap = newHeap;
        maxSize *= scalingFactor;
        add(newElement);
    }

    public void clear() {
        heap = new Comparable[maxSize];
        currentSize = 0;
    }
}
