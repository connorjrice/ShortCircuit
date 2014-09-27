package ShortCircuit.Factories;

import ShortCircuit.Controls.RegCreepControl;
import ScSDK.MapXML.CreepParams;
import ShortCircuit.PathFinding.AStarPathFinder;
import ShortCircuit.PathFinding.jMEHeuristic;
import ShortCircuit.States.Game.EnemyState;
import ShortCircuit.States.Game.GraphicsState;
import com.jme3.asset.AssetManager;
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
    private AssetManager assetManager;

    public RegCreepFactory(GraphicsState gs, EnemyState es) {
        this.gs = gs;
        this.es = es;
        this.assetManager = es.getAssetManager();
    }

    public Spatial getCreep(Vector3f spawnpoint, CreepParams cp) {
        Geometry creep_geom = new Geometry(cp.getType() + "Creep",
                gs.getUnivBox());
        creep_geom.setMaterial(assetManager.loadMaterial(
                gs.getMatLoc(cp.getType()+"Creep")));
        creep_geom.setLocalScale(cp.getSize());
        creep_geom.setLocalTranslation(spawnpoint);
        Spatial creep = creep_geom;
        creep.setUserData("Name", cp.getType());
        creep.setUserData("Health", cp.getHealth());
        creep.setUserData("Value", cp.getValue());
        creep.setUserData("Speed", cp.getSpeed());
        creep.addControl(new RegCreepControl(es, new AStarPathFinder
                (new jMEHeuristic(), es.getWorldGraph(), 5)));
        return creep;
    }
}
