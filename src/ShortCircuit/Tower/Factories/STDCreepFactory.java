package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.Controls.STDCreepControl;
import ShortCircuit.Tower.Objects.Game.CreepTraits;
import ShortCircuit.Tower.States.Game.EnemyState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * Factory for standard creeps.
 *
 * @author Connor Rice
 */
public class STDCreepFactory {

    private EnemyState cs;

    public STDCreepFactory(EnemyState _cs) {
        cs = _cs;
    }

    public Spatial getCreep(CreepTraits ct) {
        Geometry creep_geom = new Geometry("Creep", cs.getUnivBox());
        creep_geom.setMaterial(cs.getAssetManager().loadMaterial(ct.getMaterialLocation()));
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
