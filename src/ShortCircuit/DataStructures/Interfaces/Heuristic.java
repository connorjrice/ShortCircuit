package ShortCircuit.DataStructures.Interfaces;

/**
 *
 * @author Development
 */
public interface Heuristic<T> {
    
    
    public void setEndPosition(T coords);

    public int compareTo(T n);
    

    
}
