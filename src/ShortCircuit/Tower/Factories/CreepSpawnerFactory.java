package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.Controls.STDSpawnerControl;
import ShortCircuit.Tower.States.Game.CreepState;
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
    private AssetManager assetManager;
    private CreepState cs;
    
    public CreepSpawnerFactory(CreepState _cs) {
        cs = _cs;
        assetManager = cs.getAssetManager();
    }
    
    public Spatial getSpawner(int index, String creepSpawnerMat, Vector3f spawnervec, String direction) {
        Geometry spawner_geom = new Geometry("Spawner", new Box(1,1,1));
        spawner_geom.setMaterial(assetManager.loadMaterial("Materials/"+cs.getMatDir()+"/"+creepSpawnerMat+".j3m"));
        if (spawnervec.getY() == 0) {
            spawner_geom.setLocalScale(0.5f, 1.0f, 0.25f);
        } 
        else {
            spawner_geom.setLocalScale(1.0f, 0.5f, 0.25f);
        }
        spawner_geom.setLocalTranslation(spawnervec);

        Spatial spawner = spawner_geom;
        spawner.addControl(new STDSpawnerControl(cs));
        spawner.setUserData("Index", index);
        return spawner;
    }
    

}
