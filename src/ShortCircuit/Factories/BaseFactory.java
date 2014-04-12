package ShortCircuit.Factories;

import ShortCircuit.Controls.BaseControl;
import ShortCircuit.States.Game.GameState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * ASKMATTHEW: Factories, disposable or reusuable?
 * @author Connor Rice
 */
public class BaseFactory {
    private Geometry base_geom;
    
    public BaseFactory() {}
    public Geometry getBase(String baseName, Vector3f basevec, Vector3f basescale,
            AssetManager assetManager, GameState gs) {
        base_geom = new Geometry("Base", new Box(1,1,1));
        base_geom.setMaterial(assetManager.loadMaterial("Materials/"+gs.getMatDir()+baseName+".j3m"));
        base_geom.setLocalScale(basescale);
        base_geom.setLocalTranslation(basevec);
        base_geom.addControl(new BaseControl(gs, basevec));
        return base_geom;
    }
    
}
