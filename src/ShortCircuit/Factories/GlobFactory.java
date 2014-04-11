package ShortCircuit.Factories;

import ShortCircuit.Controls.GlobControl;
import ShortCircuit.States.Game.CreepState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

/**
 * Creates a glob. An annoying enemy that prevents your towers from firing,
 * and prevents you from building new towers.
 * TODO: Sounds for glob. There will be one sound, and it will be pitch modded
 * the same way the upgrade sounds are. 
 * Maybe a final sound for it popping. Some sort of popsplosion would be cool.
 * @author Connor
 */
public class GlobFactory {
    private Spatial glob;
    
    public GlobFactory(Vector3f location, int index, AssetManager assetManager, CreepState cs) {
        glob(location, index, assetManager, cs);
    }
    
    private void glob(Vector3f location, int index,
            AssetManager assetManager, CreepState cs) {
        Geometry creep_geom = new Geometry("Glob", new Sphere(32,32,1f));
        creep_geom.setMaterial(assetManager.loadMaterial("Materials/"+cs.getMatDir()+"/GiantCreep.j3m"));
        creep_geom.setLocalTranslation(location);
        glob = creep_geom;
        glob.setUserData("Name", "glob");
        glob.setUserData("Health", 5);
        glob.setUserData("TowerIndex", index);
        glob.addControl(new GlobControl(cs));
    }
    
    public Spatial getGlob() {
        return glob;
    }
    
}
