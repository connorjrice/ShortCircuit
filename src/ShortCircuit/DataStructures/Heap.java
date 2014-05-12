package ShortCircuit.DataStructures;

/**
 * Implementation of a Heap datastructure, that allows for either a Min or Max
 * Heap. It is based upon Cay Horstmann's implementation in Big Java, but uses
 * statically sized arrays instead of ArrayLists, and adds more functionality.
 * TODO: This don't work.
 * @author Connor Rice, May 2014
 */
public class Heap<T extends Comparable> {

    private Comparable<T>[] heap;
    private int maxSize;
    private int currentSize;
    private int scalingFactor;
    private boolean min;

    /**
     * Constructor, takes in an integer that is the original size of the array
     * when it is first instantiated. This structure automatically doubles the
     * size of the array being used each time the array reaches maximum
     * capacity.
     *
     * This structure can be a MinHeap (the root is the smallest element) or a
     * MaxHeap (the root is the largest element). This is determined by the
     * input parameter min. If min is true, then the Heap will be a MinHeap. If
     * min is false, the Heap will be a MaxHeap.
     *
     * @param maxSize - the original size of the statically sized array.
     * @param min - if true, structure is MinHeap. If false, MaxHeap.
     */
    public Heap(int maxSize, boolean min) {
        this.maxSize = maxSize;
        this.min = min;
        this.currentSize = 1;
        this.scalingFactor = 2;
        this.heap = new Comparable[maxSize];
    }

    /**
     * Constructor, takes in an integer that is the original size of the array
     * when the heap array is first instantiated. Without a boolean given, the
     * Heap becomes a MinHeap.
     *
     * @param maxSize
     */
    public Heap(int maxSize) {
        this.maxSize = maxSize;
        this.min = true;
        this.currentSize = 1;
        this.scalingFactor = 2;
        this.heap = new Comparable[maxSize];
    }

    /**
     * Constructor with no input parameters. If no parameters are given, then
     * the maxSize is set to 20, and the Heap will be a MinHeap.
     */
    public Heap() {
        this.maxSize = 20;
        this.min = true;
        this.currentSize = 1;
        this.scalingFactor = 2;
        this.heap = new Comparable[maxSize];
    }

    /**
     * Constructor, takes in an integer that is the original size of the array
     * when it is first instantiated. This constructor allows for an input
     * integer that determines how large a new array will be in the event that
     * the maximum size of the primitive array has been met.
     *
     * This structure can be a MinHeap (the root is the smallest element) or a
     * MaxHeap (the root is the largest element). This is determined by the
     * input parameter min. If min is true, then the Heap will be a MinHeap. If
     * min is false, the Heap will be a MaxHeap.
     *
     * Additionally, an integer is given that determines how much larger the
     * array should get in the event of reaching full capacity. The array will
     * grow thusly - maxSize * scalingFactor.
     *
     * @param maxSize - the original size of the statically sized array.
     * @param min - if true, structure is MinHeap. If false, MaxHeap.
     * @param scalingFactor - a multiplier that determines how much larger the
     * array will get when it reaches it's full capacity.
     */
    public Heap(int maxSize, boolean min, int scalingFactor) {
        this.maxSize = maxSize;
        this.min = min;
        this.scalingFactor = scalingFactor;
        this.currentSize = 1;
        this.heap = new Comparable[maxSize];
    }

    /**
     * Adds a new element to the Heap.
     *
     * @param newElement Comparable element to be added to the Heap.
     */
    public void add(Comparable newElement) {
        if (currentSize < maxSize - 1) {
            int index = currentSize - 1;
            currentSize++;


            // Moves parents based upon whether the heap is a minheap or maxheap
            if (min) {
                while (index > 1 && getParent(index).compareTo(newElement) > 0) {
                    heap[index] = getParent(index);
                    index = getParentIndex(index);
                }
            } else {
                while (index > 1 && getParent(index).compareTo(newElement) < 0) {
                    heap[index] = getParent(index);
                    index = getParentIndex(index);
                }
            }

            heap[index] = newElement;

        } else {
            doubleCapacity(newElement);
        }
    }

    /**
     * Returns the root of the heap without removing it from the heap.
     *
     * @return The root of the Heap, either smallest or largest element
     * depending on whether or not the Heap is a MinHeap or a MaxHeap.
     */
    public Comparable peek() {
        return heap[1];
    }

    /**
     * Returns the root of the heap and removes it from the heap.
     *
     * @return The root of the Heap, which is either the smallest element (if
     * the Heap is a MinHeap) or the largest element (if the Heap is a MaxHeap.)
     */
    public Comparable remove() {
        Comparable minimum = heap[1];
        Comparable last = heap[currentSize - 1];
        if (currentSize > 1) {
            heap[1] = last;
            fixHeap();
        }
        currentSize--;
        return minimum;
    }

    /**
     * Internal method to keep the structure of the Heap intact when removing
     * elements from the Heap.
     */
    private void fixHeap() {
        Comparable root = heap[1];
        int index = 1;
        boolean done = false;
        while (!done) {
            int childIndex = getLeftChildIndex(index);
            if (childIndex <= getSize()) { // prevents IndexOutOfBoundsException
                Comparable child = getLeftChild(index);
                if (min) { // Performs operation for MinHeap
                    if (getRightChildIndex(index) <= getSize()
                            && getRightChild(index).compareTo(child) < 0) {
                        childIndex = getRightChildIndex(index);
                        child = getRightChild(index);
                    }
                    if (child.compareTo(root) < 0) {
                        heap[index] = child;
                        index = childIndex;
                    } else {
                        done = true;
                    }
                } else { // Performs operation for MaxHeap.
                    if (getRightChildIndex(index) <= getSize()
                            && getRightChild(index).compareTo(child) > 0) {
                        childIndex = getRightChildIndex(index);
                        child = getRightChild(index);
                    }
                    if (child.compareTo(root) > 0) {
                        heap[index] = child;
                        index = childIndex;
                    } else {
                        done = true;
                    }
                }
            } else {
                done = true;
            }
            heap[index] = root;
        }
    }

    private int getLeftChildIndex(int index) {
        return 2 * index;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getParentIndex(int index) {
        return index / 2;
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
     * Returns the number of elements stored within the Heap.
     *
     * @return Current size of the Heap (number of elements stored within Heap.)
     */
    public int getSize() {
        return currentSize - 1;
    }

    /**
     * Returns whether or not the Heap is empty.
     *
     * @return True if Heap is empty. False if Heap is not empty.
     */
    public boolean isEmpty() {
        return currentSize == 1;
    }
   
    /**
     * Returns a copy of the internal Heap array.
     * @return An array of comparable elements, sorted into a Heap.
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
        this.heap = new Comparable[maxSize];
    }

}