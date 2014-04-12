package ShortCircuit.Factories;

import ShortCircuit.States.Game.GameState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 *
 * @author Connor Rice
 */
public class FloorFactory {
    private GameState gs;
    private AssetManager assetManager;
    
    public FloorFactory(GameState _gs) {
        gs = _gs;
        assetManager = gs.getAssetManager();
    }
    
    public Geometry getFloor(Vector3f floorscale, 
            String floorName ) {
        Geometry floor_geom = new Geometry("Floor", gs.getUnivBox());
        floor_geom.setMaterial(assetManager.loadMaterial(floorName));
        floor_geom.setLocalScale(floorscale);
        return floor_geom;
    }
    
    
}
