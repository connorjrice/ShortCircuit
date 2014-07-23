package ShortCircuit.Factories;

import ShortCircuit.Controls.GlobControl;
import ShortCircuit.States.Game.EnemyState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * Creates a glob. An annoying enemy that prevents your towers from firing, and
 * prevents you from building new towers. TODO: Explosion animation/smaller glob
 * per pop
 *
 * @author Connor Rice
 */
public class GlobFactory {

    private EnemyState cs;

    public GlobFactory(EnemyState _cs) {
        cs = _cs;
    }

    public Spatial getGlob(Vector3f location, int index) {
        Geometry creep_geom = new Geometry("Glob", cs.getGlobSphere());
        creep_geom.setMaterial(cs.getAssetManager().loadMaterial(
                "Materials/" + cs.getMatDir() + "/GiantCreep.j3m"));
        creep_geom.setLocalTranslation(location);
        Spatial glob = creep_geom;
        glob.setUserData("Name", "glob");
        glob.setUserData("Health", 5);
        glob.setUserData("TowerIndex", index);
        glob.addControl(new GlobControl(cs));
        return glob;
    }
}
