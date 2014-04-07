package ShortCircuit.Factories;

import ShortCircuit.Controls.CreepSpawnerControl;
import ShortCircuit.States.Game.CreepState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Connor Rice
 */
public class CreepSpawnerFactory {
    private Spatial spawner;
    
    public CreepSpawnerFactory(int index, String creepSpawnerMat, Vector3f spawnervec, String direction,
            AssetManager assetManager, CreepState cs) {
        createCreepSpawner(index, creepSpawnerMat, spawnervec, direction, assetManager, cs);
    }
    
    
    private void createCreepSpawner(int index, String creepSpawnerMat, Vector3f spawnervec, String direction,
            AssetManager assetManager, CreepState cs) {
        Geometry spawner_geom = new Geometry("Spawner", new Box(1,1,1));
        spawner_geom.setMaterial(assetManager.loadMaterial("Materials/"+cs.getMatDir()+"/"+creepSpawnerMat+".j3m"));
        if (spawnervec.getY() == 0) {
            spawner_geom.setLocalScale(0.5f, 1.0f, 0.25f);
        } 
        else {
            spawner_geom.setLocalScale(1.0f, 0.5f, 0.25f);
        }
        spawner_geom.setLocalTranslation(spawnervec);

        spawner = spawner_geom;
        spawner.addControl(new CreepSpawnerControl(cs));
        spawner.setUserData("Index", index);
        spawner.setUserData("Direction", direction);
    }
    
    public Spatial getSpawner() {
        return spawner;
    }
    
}
