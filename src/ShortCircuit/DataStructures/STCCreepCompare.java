package ShortCircuit.DataStructures;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Comparator;

/**
 *
 * @author clarence
 */
public class STCCreepCompare implements Comparator<Spatial>{
    
    private Vector3f origin;
    
    public STCCreepCompare(Vector3f _origin) {
        origin = _origin;
    }
    // TODO: Document CreepCompare
    @Override
    public int compare(Spatial lhs, Spatial rhs) {
        if (lhs.getLocalTranslation().distance(origin) < rhs.getLocalTranslation().distance(origin)) {
            return -1;
        }
        else if (lhs.getLocalTranslation().distance(origin) > rhs.getLocalTranslation().distance(origin)) {
            return 1;
        }
        else {
            return 0;
        }        
    }
    
}
