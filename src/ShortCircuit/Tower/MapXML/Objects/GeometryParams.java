package ShortCircuit.Tower.MapXML.Objects;

import com.jme3.math.Vector3f;

/**
 *
 * @author Development
 */
public class GeometryParams {
    private Vector3f floorscale;
    
    public GeometryParams(Vector3f floorscale) {
        this.floorscale = floorscale;

    }
    
    public Vector3f getFloorScale() {
        return floorscale;
    }
    
}
