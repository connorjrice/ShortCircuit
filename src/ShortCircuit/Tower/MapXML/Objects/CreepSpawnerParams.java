package ShortCircuit.Tower.MapXML.Objects;

import com.jme3.math.Vector3f;

/**
 *
 * @author Connor Rice
 */
public class CreepSpawnerParams {
    Vector3f vec;
    String orientation;
    
    public CreepSpawnerParams(Vector3f vec, String orientation) {
        this.vec = vec;
        this.orientation = orientation;
    }
    
}
