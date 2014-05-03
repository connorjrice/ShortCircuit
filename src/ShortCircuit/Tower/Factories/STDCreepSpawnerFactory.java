package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.Controls.CreepSpawnerControl;
import ShortCircuit.Tower.MapXML.Objects.CreepSpawnerParams;
import ShortCircuit.Tower.States.Game.GraphicsState;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * Factory for standard creep spawners.
 * @author Connor Rice
 */
public class STDCreepSpawnerFactory {

    private GraphicsState gs;
    private AssetManager assetManager;

    public STDCreepSpawnerFactory(GraphicsState gs) {
        this.gs = gs;
        this.assetManager = this.gs.getAssetManager();
    }

    public CreepSpawnerParams getSpawner(CreepSpawnerParams csp) {
        Geometry spawner_geom = new Geometry("Spawner", new Box(1, 1, 1));
        spawner_geom.setMaterial(assetManager.loadMaterial(gs.getCreepSpawnerMatLoc()));
        spawner_geom.setLocalTranslation(csp.getVec());
        if (csp.getOrientation().equals("horizontal")) {
            spawner_geom.setLocalScale(gs.getCreepSpawnerHorizontalScale());            
        } else if (csp.getOrientation().equals("vertical")) {
            spawner_geom.setLocalScale(gs.getCreepSpawnerVerticalScale());            
        }
        Spatial spawner = spawner_geom;
        csp.setSpatial(spawner);
        csp.setIndex();
        CreepSpawnerControl csc = new CreepSpawnerControl(gs.getEnemyState());
        spawner.addControl(csc);
        csp.setControl(csc);
        return csp;
    }
}
