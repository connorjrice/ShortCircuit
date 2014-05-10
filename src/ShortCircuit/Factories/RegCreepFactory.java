package ShortCircuit.Factories;

import ShortCircuit.Controls.RegCreepControl;
import ShortCircuit.MapXML.Objects.CreepParams;
import ShortCircuit.States.Game.EnemyState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * Factory for standard creeps.
 *
 * @author Connor Rice
 */
public class RegCreepFactory {

    private EnemyState cs;

    public RegCreepFactory(EnemyState _cs) {
        cs = _cs;
    }

    public Spatial getCreep(Vector3f spawnpoint, CreepParams cp) {
        Geometry creep_geom = new Geometry("Creep", cs.getUnivBox());
        creep_geom.setMaterial(cs.getAssetManager().loadMaterial(cs.getCreepMatLoc(cp.getType())));
        creep_geom.setLocalScale(cp.getSize());
        creep_geom.setLocalTranslation(spawnpoint);
        Spatial creep = creep_geom;
        creep.setUserData("Name", cp.getType());
        creep.setUserData("Health", cp.getHealth());
        creep.setUserData("Value", cp.getValue());
        creep.setUserData("Speed", cp.getSpeed());
        creep.addControl(new RegCreepControl(cs));
        return creep;
    }
}
