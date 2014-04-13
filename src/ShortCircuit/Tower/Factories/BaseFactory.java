package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.Controls.BaseControl;
import ShortCircuit.Tower.States.Game.GameState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 * ASKMATTHEW: Factories, disposable or reusuable?
 * @author Connor Rice
 */
public class BaseFactory {
    private GameState gs;
    private AssetManager assetManager;
    
    public BaseFactory(GameState _gs) {
        gs = _gs;
        assetManager = gs.getAssetManager();
    }
    public Geometry getBase(String baseName, Vector3f basevec, Vector3f basescale) {
        Geometry base_geom = new Geometry("Base", gs.getUnivBox());
        base_geom.setMaterial(assetManager.loadMaterial("Materials/"+gs.getMatDir()+baseName+".j3m"));
        base_geom.setLocalScale(basescale);
        base_geom.setLocalTranslation(basevec);
        base_geom.addControl(new BaseControl(gs, basevec));
        return base_geom;
    }
    
}
