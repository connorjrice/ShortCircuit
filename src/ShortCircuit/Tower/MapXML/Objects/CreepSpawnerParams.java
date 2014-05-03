package ShortCircuit.Tower.MapXML.Objects;

import ShortCircuit.Tower.Controls.CreepSpawnerControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Connor Rice
 */
public class CreepSpawnerParams {
    private Vector3f vec;
    private String orientation;
    private int index;
    private CreepSpawnerControl control;
    private Spatial spatial;
    
    public CreepSpawnerParams(Vector3f vec, String orientation, int index) {
        this.vec = vec;
        this.orientation = orientation;
        this.index = index;
    }
    
    public Vector3f getVec() {
        return vec;
    }
    
    public String getOrientation() {
        return orientation;
    }
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex() {
        spatial.setUserData("Index", index);
    }
    
    public void setControl(CreepSpawnerControl csc) {
        this.control = csc;
    }
    
    public CreepSpawnerControl getControl() {
        return control;
    }
    
    public void setSpatial(Spatial s) {
        this.spatial = s;
    }
    
    public Spatial getSpatial() {
        return spatial;
    }
    
}
