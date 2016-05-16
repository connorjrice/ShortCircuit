package datastructures;

import java.util.LinkedHashSet;

/**
 * @author Connor
 * @param <E>
 */

public class CachedLinkedHashSet<E> extends LinkedHashSet<E> {
    private E last = null;
    
    @Override
    public boolean add(E e) {
        last = e;
        super.add(e);
        return true;
    }
    
    public E getLast() {
        return last;
    }

}
