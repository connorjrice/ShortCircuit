package ScSDK.MapXML;

import ShortCircuit.Controls.TowerControl;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.io.IOException;

/**
 *
 * @author Connor Rice
 */
public class TowerParams implements Savable {
    
    private Vector3f vec;
    private boolean starter;
    private int index;
    private String type;
    private Vector3f scale;
    private Spatial towerSpatial;
    private float beamwidth;
    
    public TowerParams(Vector3f vec,  boolean starter, int index, 
            float beamwidth) {
        this.vec = vec;
        this.starter = starter;
        this.index = index;
        this.beamwidth = beamwidth;
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

    
    public String getType() {
        return type;
    }
    
    public int getIndex() {
        return index;
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule in = im.getCapsule(this);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule out = ex.getCapsule(this);
    }

    public TowerControl getControl(Class<TowerControl> aClass) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    

    
}
