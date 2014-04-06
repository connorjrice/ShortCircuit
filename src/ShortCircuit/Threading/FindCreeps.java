package ShortCircuit.Threading;


import ShortCircuit.DataStructures.STC;
import com.jme3.scene.Spatial;

/**
 * Wrapper for STC<Spatial> to be called on a seperate thread
 * @author Connor Rice
 */
public class FindCreeps {
    
    public STC<Spatial> reach;
    
    public FindCreeps(STC<Spatial> _reach) {
        reach = _reach;
    }
    
    public STC<Spatial> getReach() {
        return reach;
    }
}
