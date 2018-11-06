package DataStructures.Nodes;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Comparator;

/**
 * This compares the distance of two spatials, and returns the relationship
 * between the distance of each spatial to the origin.
 *
 * @author Connor Rice
 */
public class STCCreepCompare implements Comparator<Spatial> {

    private Vector3f origin;

    public STCCreepCompare(Vector3f _origin) {
        origin = _origin;
    }

    @Override
    public int compare(Spatial lhs, Spatial rhs) {
        if (lhs.getLocalTranslation().distance(origin) < rhs.getLocalTranslation().distance(origin)) {
            return -1;
        } else if (lhs.getLocalTranslation().distance(origin) > rhs.getLocalTranslation().distance(origin)) {
            return 1;
        } else {
            return 0;
        }
    }
}
