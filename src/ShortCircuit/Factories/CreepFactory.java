package ShortCircuit.Factories;

import ShortCircuit.Controls.STDCreepControl;
import ShortCircuit.Objects.CreepTraits;
import ShortCircuit.States.Game.CreepState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 *
 * @author Connor Rice
 */
public class CreepFactory {
    private Spatial creep;
    
    public CreepFactory(CreepTraits ct, AssetManager assetManager, CreepState cs) {
        createCreep(ct, assetManager, cs);
    }
    
    private void createCreep(CreepTraits ct, 
            AssetManager assetManager, CreepState cs) {
        Geometry creep_geom = new Geometry("Creep", cs.getUnivBox());
        creep_geom.setMaterial(assetManager.loadMaterial(ct.getMaterialLocation()));
        creep_geom.setLocalScale(ct.getSize());
        creep_geom.setLocalTranslation(ct.getSpawnPoint());
        creep = creep_geom;
        creep.setUserData("Name", ct.getName());
        creep.setUserData("Parent", ct.getSpawnIndex());
        creep.setUserData("Health", ct.getHealth());
        creep.setUserData("Speed", ct.getSpeed());
        creep.setUserData("Type", ct.getType());
        if (ct.getDirection().equals("north")) {
            creep.setUserData("Direction", new Vector3f(0f, ct.getSpeed(), 0f));
        } else if (ct.getDirection().equals("south")) {
            creep.setUserData("Direction", new Vector3f(0f, -ct.getSpeed(), 0f));
        } else if (ct.getDirection().equals("east")) {
            creep.setUserData("Direction", new Vector3f(-ct.getSpeed(), 0f, 0f));
        } else if (ct.getDirection().equals("west")) {
            creep.setUserData("Direction", new Vector3f(ct.getSpeed(), 0f, 0f));
        } else {
            creep.setUserData("Direction", new Vector3f(0f,0f,ct.getSpeed()));
        }
        
        creep.addControl(new STDCreepControl(cs));
    }
    
    public Spatial getCreep() {
        return creep;
    }
    
}
