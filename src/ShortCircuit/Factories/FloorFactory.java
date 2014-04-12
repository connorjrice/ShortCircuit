package ShortCircuit.Factories;

import ShortCircuit.States.Game.GameState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Connor Rice
 */
public class FloorFactory {
    
    private Geometry floor_geom;
    
    public FloorFactory() {}
    
    public Geometry getFloor(Vector3f floorscale, 
            String floorName, AssetManager assetManager, GameState gs) {
        floor_geom = new Geometry("Floor", new Box(1,1,1));
        floor_geom.setMaterial(assetManager.loadMaterial(floorName));
        floor_geom.setLocalScale(floorscale);
        return floor_geom;
    }
    
    
}
