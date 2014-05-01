package ShortCircuit.Tower.MapXML.Objects;

import com.jme3.math.Vector3f;

/**
 *
 * @author Connor Rice
 */
public class BaseParams {
    private Vector3f baseVec;
    private Vector3f baseScale;
    
    public BaseParams(Vector3f baseVec, Vector3f baseScale) {
        this.baseVec = baseVec;
        this.baseScale = baseScale;
    }
    
    public Vector3f getBaseVec() {
        return baseVec;
    }
    
    public Vector3f getBaseScale() {
        return baseScale;
    }
    
}
