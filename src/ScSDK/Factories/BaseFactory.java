package ScSDK.Factories;

import ScSDK.IO.BuildState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * @author Connor Rice
 */
public class BaseFactory {
    private BuildState bs;
    
    public BaseFactory(BuildState bs) {
        this.bs = bs;
    }
    
    /**
     * Returns a player base geometry.
     * @param basevec - Local translation for base.
     * @param basescale - Local scale for base.
     * @return 
     */
    public Geometry getBase(Vector3f basevec, Vector3f basescale) {
        Geometry base_geom = new Geometry("Base", new Box(1,1,1));
        base_geom.setMaterial(bs.getMaterial("Base"));
        base_geom.setLocalScale(basescale);
        base_geom.setLocalTranslation(basevec);
        return base_geom;
    }
    
}
