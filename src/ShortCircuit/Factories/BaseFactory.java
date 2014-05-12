package ShortCircuit.Factories;

import ShortCircuit.States.Game.GraphicsState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * @author Connor Rice
 */
public class BaseFactory {
    private GraphicsState gs;
    
    public BaseFactory(GraphicsState gs) {
        this.gs = gs;
    }
    public Geometry getBase(Vector3f basevec, Vector3f basescale) {
        Geometry base_geom = new Geometry("Base", new Box(1,1,1));
        base_geom.setMaterial(gs.getMaterial("Base"));
        base_geom.setLocalScale(basescale);
        base_geom.setLocalTranslation(basevec);
        return base_geom;
    }
    
}
