package ScSDK.Factories;

import ScSDK.IO.BuildState;
import ScSDK.MapXML.CreepSpawnerParams;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * Factory for standard creep spawners.
 * @author Connor Rice
 */
public class CreepSpawnerFactory {

    private BuildState bs;

    public CreepSpawnerFactory(BuildState bs) {
        this.bs = bs;
    }

    /**
     * Returns a CreepSpawnerParams object.
     * @param csp
     * @return 
     */
    public CreepSpawnerParams getSpawner(CreepSpawnerParams csp) {
        Geometry spawner_geom = new Geometry("Spawner", new Box(1, 1, 1));
        spawner_geom.setMaterial((Material)bs.getMaterial("CreepSpawner"));
        spawner_geom.setLocalTranslation(csp.getVec());
        if (csp.getOrientation().equals("horizontal")) {
            spawner_geom.setLocalScale(bs.getCreepSpawnerHorizontalScale());            
        } else if (csp.getOrientation().equals("vertical")) {
            spawner_geom.setLocalScale(bs.getCreepSpawnerVerticalScale());            
        }
        Spatial spawner = spawner_geom;
        csp.setSpatial(spawner);
        csp.setIndex();
        return csp;
    }
}
