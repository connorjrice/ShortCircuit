package ShortCircuit.Threading;

import ShortCircuit.Controls.RegCreepControl;
import ShortCircuit.DataStructures.Nodes.GraphNode;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;

/**
 * Runnable for moving creeps.
 * TODO: Interpolation with pathfinding
 * @author clarence
 */
public class MoveCreep implements Runnable {

    private RegCreepControl cc;
    private String baseCoords;
    private BoundingVolume baseBounds;


    public MoveCreep(RegCreepControl cc) {
        this.cc = cc;
        this.baseCoords = cc.getFormattedBaseCoords();
        this.baseBounds = cc.getBaseBounds();
    }

    public void run() {
        if (cc.path == null) {
            cc.baseCoords =  cc.getFormattedBaseCoords();
            cc.path = cc.pathFinder.getPath(cc.getFormattedCoords(), baseCoords);
        }
        if(cc.getSpatial().getWorldBound().intersects(baseBounds)) {
            cc.removeCreep(false);
        } else {
            if (!cc.path.getEndReached()) {
                GraphNode nextGraph = cc.getWorldGraph().getNode(cc.path.getNextPathNode());
                float[] nextCoords = nextGraph.getCoordArray();
                Vector3f newLoc = new Vector3f(nextCoords[0], nextCoords[1], 0.1f);
                cc.getSpatial().setLocalTranslation(newLoc);
            } else {
                cc.path = cc.pathFinder.getPath(cc.getFormattedCoords(), baseCoords);
                GraphNode nextGraph = cc.getWorldGraph().getNode(cc.path.getNextPathNode());
                float[] nextCoords = nextGraph.getCoordArray();
                Vector3f newLoc = new Vector3f(nextCoords[0], nextCoords[1], 0.1f);
                cc.getSpatial().setLocalTranslation(newLoc);
            }
        }

    }
}
