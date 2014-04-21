package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.States.Game.GameState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 * Factory for floor.
 * @author Connor Rice
 */
public class FloorFactory {
    private GameState gs;
    
    public FloorFactory(GameState _gs) {
        gs = _gs;
    }
    
    public Geometry getFloor(Vector3f floorscale, 
            String floorName ) {
        Geometry floor_geom = new Geometry("Floor", gs.getUnivBox());
        floor_geom.setMaterial(gs.getAssetManager().loadMaterial(floorName));
        floor_geom.setLocalScale(floorscale);
        return floor_geom;
    }
    
    
}
