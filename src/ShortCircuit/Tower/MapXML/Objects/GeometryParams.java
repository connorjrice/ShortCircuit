package ShortCircuit.Tower.MapXML.Objects;

import com.jme3.math.Vector3f;

/**
 *
 * @author Development
 */
public class GeometryParams {
    private Vector3f camLoc;
    private Vector3f floorscale;
    private Vector3f baseVec;
    private Vector3f baseScale;
    private Vector3f towerBuiltSize;
    private Vector3f towerUnbuiltSize;
    private Vector3f towerBuiltSelected;
    private Vector3f towerUnbuiltSelected;

    
    public GeometryParams(Vector3f camLoc, Vector3f floorscale, Vector3f baseVec, 
            Vector3f baseScale, Vector3f towerBuiltSize, Vector3f towerUnbuiltSize,
            Vector3f towerBuiltSelected, Vector3f towerUnbuiltSelected) {
        this.camLoc = camLoc;
        this.floorscale = floorscale;
        this.baseVec = baseVec;
        this.baseScale = baseScale;
        this.towerBuiltSize = towerBuiltSize;
        this.towerUnbuiltSize = towerUnbuiltSize;
        this.towerBuiltSelected = towerBuiltSelected;
        this.towerUnbuiltSelected = towerUnbuiltSelected;

    }
    
    public Vector3f getCamLoc() {
        return camLoc;
    }
    
    public Vector3f getFloorScale() {
        return floorscale;
    }
    
    public Vector3f getBaseVec() {
        return baseVec;
    }
    
    public Vector3f getBaseScale() {
        return baseScale;
    }
    
    public Vector3f getTowerBuiltSize() {
        return towerBuiltSize;
    }
    
    public Vector3f getTowerUnbuiltSize() {
        return towerUnbuiltSize;
    }
    
    public Vector3f getTowerBuiltSelected() {
        return towerBuiltSelected;
    }
    
    public Vector3f getTowerUnbuiltSelected() {
        return towerUnbuiltSelected;
    }
    
}
