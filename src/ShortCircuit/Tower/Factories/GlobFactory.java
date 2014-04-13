package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.Controls.GlobControl;
import ShortCircuit.Tower.States.Game.CreepState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * Creates a glob. An annoying enemy that prevents your towers from firing,
 * and prevents you from building new towers.
 * TODO: Sounds for glob. There will be one sound, and it will be pitch modded
 * the same way the upgrade sounds are. 
 * Maybe a final sound for it popping. Some sort of popsplosion would be cool.
 * @author Connor
 */
public class GlobFactory {
    private AssetManager assetManager;
    private CreepState cs;
    
    public GlobFactory(CreepState _cs){
        cs = _cs;
        assetManager = cs.getAssetManager();

    }
    
    
    public Spatial getGlob(Vector3f location, int index) {
        Geometry creep_geom = new Geometry("Glob", cs.getGlobSphere());
        creep_geom.setMaterial(assetManager.loadMaterial("Materials/"+cs.getMatDir()+"/GiantCreep.j3m"));
        creep_geom.setLocalTranslation(location);
        Spatial glob = creep_geom;
        glob.setUserData("Name", "glob");
        glob.setUserData("Health", 5);
        glob.setUserData("TowerIndex", index);
        glob.addControl(new GlobControl(cs));
        return glob;
    }

}