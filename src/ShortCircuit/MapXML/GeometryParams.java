package ShortCircuit.MapXML;

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
    private Vector3f creepSpawnerHorizontalScale;
    private Vector3f creepSpawnerVerticalScale;
    private float beamwidth;

    
    public GeometryParams(Vector3f camLoc, Vector3f floorscale, Vector3f baseVec, 
            Vector3f baseScale, Vector3f towerBuiltSize, Vector3f towerUnbuiltSize,
            Vector3f towerBuiltSelected, Vector3f towerUnbuiltSelected, 
            Vector3f creepSpawnerHorizontalScale, Vector3f creepSpawnerVerticalScale, float beamwidth) {
        this.camLoc = camLoc;
        this.floorscale = floorscale;
        this.baseVec = baseVec;
        this.baseScale = baseScale;
        this.towerBuiltSize = towerBuiltSize;
        this.towerUnbuiltSize = towerUnbuiltSize;
        this.towerBuiltSelected = towerBuiltSelected;
        this.towerUnbuiltSelected = towerUnbuiltSelected;
        this.creepSpawnerHorizontalScale = creepSpawnerHorizontalScale;
        this.creepSpawnerVerticalScale = creepSpawnerVerticalScale;
        this.beamwidth = beamwidth;
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
    
    public Vector3f getCreepSpawnerHorizontalScale() {
        return creepSpawnerHorizontalScale;
    }
    
    public Vector3f getCreepSpawnerVerticalScale() {
        return creepSpawnerVerticalScale;
    }
    
    public float getBeamWidth() {
        return beamwidth;
    }
    
}
