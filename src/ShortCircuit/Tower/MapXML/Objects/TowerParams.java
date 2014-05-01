package ShortCircuit.Tower.MapXML.Objects;

import com.jme3.math.Vector3f;

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
    
    public TowerParams(float x, float y, float z, boolean starter) {
        this.vec = new Vector3f(x, y, z);
        this.starter = starter;
    }
    
    public void setScale(Vector3f s) {
        scale = s;
    }
    
    public void setIndex(int i) {
        index = i;
    }
    
    public void setType(String t) {
        type = t;
    }
    
    public Vector3f getTowerVec() {
        return vec;
    }
    
    public boolean getIsStater() {
        return starter;
    }
       
    public Vector3f getScale() {
        return scale;
    }
    
    public String getType() {
        return type;
    }
    
    public int getIndex() {
        return index;
    }

    
}
