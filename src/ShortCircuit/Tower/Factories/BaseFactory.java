package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.States.Game.GameState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 * @author Connor Rice
 */
public class BaseFactory {
    private GameState gs;
    
    public BaseFactory(GameState _gs) {
        gs = _gs;
    }
    public Geometry getBase(String baseName, Vector3f basevec, Vector3f basescale) {
        Geometry base_geom = new Geometry("Base", gs.getUnivBox());
        base_geom.setMaterial(gs.getAssetManager().loadMaterial("Materials/"+gs.getMatDir()+baseName+".j3m"));
        base_geom.setLocalScale(basescale);
        base_geom.setLocalTranslation(basevec);
        return base_geom;
    }
    
}
