package ShortCircuit.Factories;

import ShortCircuit.Controls.RegCreepControl;
import ShortCircuit.MapXML.CreepParams;
import ShortCircuit.States.Game.EnemyState;
import ShortCircuit.States.Game.GraphicsState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * Factory for standard creeps.
 *
 * @author Connor Rice
 */
public class RegCreepFactory {

    private GraphicsState gs;
    private EnemyState es;

    public RegCreepFactory(GraphicsState gs, EnemyState es) {
        this.gs = gs;
        this.es = es;
    }

    public Spatial getCreep(Vector3f spawnpoint, CreepParams cp) {
        Geometry creep_geom = new Geometry(cp.getType()+"Creep", gs.getUnivBox());
        creep_geom.setMaterial(gs.getMaterial(cp.getType()+"Creep"));
        System.out.println("Creep"+cp.getType());
        creep_geom.setLocalScale(cp.getSize());
        creep_geom.setLocalTranslation(spawnpoint);
        Spatial creep = creep_geom;
        creep.setUserData("Name", cp.getType());
        creep.setUserData("Health", cp.getHealth());
        creep.setUserData("Value", cp.getValue());
        creep.setUserData("Speed", cp.getSpeed());
        creep.addControl(new RegCreepControl(es));
        return creep;
    }
}
