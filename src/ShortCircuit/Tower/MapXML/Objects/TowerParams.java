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
    
    public TowerParams(Vector3f vec,  boolean starter) {
        this.vec = vec;
        this.starter = starter;
        type = "unbuilt";
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
       
    
    public String getType() {
        return type;
    }
    
    public int getIndex() {
        return index;
    }

    
}
