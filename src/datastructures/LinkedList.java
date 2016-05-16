package datastructures;

public class LinkedList<T> {

    private int size = 0; // Size of the list
    private LinkedListNode<T> root; // First element in list
    private LinkedListNode<T> last; // Last element of the list

    public LinkedList() {}
 
    
    public void init(LinkedListNode<T> firstNode) {
	root = firstNode;
        last = firstNode;
	size++;
    }
    
    public void add(T newElement) { // newElement = element to be added to list
        LinkedListNode<T> newNode = new LinkedListNode<T>(newElement);
        if (root == null) { // If there is no first
            init(newNode); // Initialize this list
        } else {
            last.setNeighbor(newNode);
            last = newNode;
        }
        size++;

    } 

    public void remove(T doomedElement) {
        if (doomedElement.equals(root.getElement())) {
            removeFirst();
        }
        else {
            LinkedListNode<T> current = root.getNeighbor();
            LinkedListNode<T> prev = root;
            boolean done = false;
            while (current != null && !done) {
                if (current.getElement().equals(doomedElement)) {
                    prev.setNeighbor(current.getNeighbor());
                    size--;
                }
                else {
                    current = current.getNeighbor();
                }
            }
        }

    } 

    public T get(T searchElement) {
       LinkedListNode<T> current = root;
       while (current != null) {
	   if (current.getElement() == searchElement) { 
               return current.getElement();
	   }
	   else {
	       current = current.getNeighbor();
	   }
       }
       return null;
   }


    public void push(T newElement) {
	LinkedListNode<T> key = root;
	LinkedListNode<T> newfirst = new LinkedListNode<T>(newElement);
	root = newfirst;
	root.setNeighbor(key);
	size++;
    }
    
    public void removeFirst() {
	root = root.getNeighbor();
	size--;
    }
    
    public LinkedListNode<T> getFirstNode() {
	return root; 
    }

    public T getFirstElement() {
	return root.getElement();
    }

    public int length() {
	return size;
    }
   
    
    public boolean isEmpty () {
        return (size == 0);
    }
    
    public void clear() {
        root = null;
        last = null;
        size = 0;
    }

}







