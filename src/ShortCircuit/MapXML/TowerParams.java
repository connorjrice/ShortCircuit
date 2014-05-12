package ShortCircuit.MapXML;

import ShortCircuit.Controls.TowerControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Connor Rice
 */
public class TowerParams {
    
    private Vector3f vec;
    private boolean starter;
    private int index;
    private String type;
    private Vector3f scale;
    private Spatial towerSpatial;
    private TowerControl towerControl;
    private float beamwidth;
    
    public TowerParams(Vector3f vec,  boolean starter, int index) {
        this.vec = vec;
        this.starter = starter;
        this.index = index;
    }
    
    public void setScale(Vector3f s) {
        scale = s;
        getSpatial().setLocalScale(scale);
    }
    
    public void setIndex() {
        getSpatial().setUserData("Index", index);
    }
    
    public void setType(String t) {
        type = t;
        getSpatial().setUserData("Type", t);
    }
    
    public Vector3f getTowerVec() {
        return vec;
    }
    
    public boolean getIsStarter() {
        return starter;
    }
    
    public void setBeamWidth(float beamwidth) {
        this.beamwidth = beamwidth;
        towerControl.setBeamWidth(beamwidth);
    }
    
    public float getBeamWidth() {
        return beamwidth;
    }
    
    public void setSpatial(Spatial s) {
        this.towerSpatial = s;
    }
    
    public Spatial getSpatial() {
        return towerSpatial;
    }
       
    public void setControl(TowerControl c) {
        this.towerControl = c;
    }
 
    
    public TowerControl getControl() {
        return towerControl;
    }
    
    public String getType() {
        return type;
    }
    
    public int getIndex() {
        return index;
    }

    
}
