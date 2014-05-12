package ShortCircuit.DataStructures.Interfaces;


import ShortCircuit.DataStructures.Objects.Path;

/**
 *
 * @author Development
 */
public interface PathFinder {

    public Path pathFind(Path curPath);
    
    public Path clonePath(Path p);
    

    
}
