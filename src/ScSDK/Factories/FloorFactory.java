package ScSDK.Factories;

import ScSDK.IO.BuildState;
import com.jme3.scene.Geometry;

/**
 *
 * @author Connor Rice
 */
public class FloorFactory {

    private BuildState bs;

    public FloorFactory(BuildState bs) {
        this.bs = bs;
    }

    public Geometry getFloor() {
        Geometry floor_geom = new Geometry("Floor", bs.getUnivBox());
        floor_geom.setMaterial(bs.getMaterial("Floor"));
        floor_geom.setLocalScale(bs.getFloorScale());
        return floor_geom;
    }
}
