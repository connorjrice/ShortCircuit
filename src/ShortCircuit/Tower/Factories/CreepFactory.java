package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.Controls.STDCreepControl;
import ShortCircuit.Tower.Objects.CreepTraits;
import ShortCircuit.Tower.States.Game.CreepState;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 *
 * @author Connor Rice
 */
public class CreepFactory {
    private AssetManager assetManager;
    private CreepState cs;
    
    public CreepFactory(CreepState _cs) {
        cs = _cs;
        assetManager = cs.getAssetManager();
    }
    
    public Spatial getCreep(CreepTraits ct) {
        Geometry creep_geom = new Geometry("Creep", cs.getUnivBox());
        creep_geom.setMaterial(assetManager.loadMaterial(ct.getMaterialLocation()));
        creep_geom.setLocalScale(ct.getSize());
        creep_geom.setLocalTranslation(ct.getSpawnPoint());
        Spatial creep = creep_geom;
        creep.setUserData("Name", ct.getName());
        creep.setUserData("Parent", ct.getSpawnIndex());
        creep.setUserData("Health", ct.getHealth());
        creep.setUserData("Value", ct.getValue());
        creep.setUserData("Speed", ct.getSpeed());
        creep.addControl(new STDCreepControl(cs));
        return creep;
 
    }
    
    
}
