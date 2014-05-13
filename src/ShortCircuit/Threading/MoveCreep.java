package ShortCircuit.Threading;

import ShortCircuit.Controls.RegCreepControl;
import ShortCircuit.DataStructures.Nodes.GraphNode;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import java.text.DecimalFormat;

/**
 * Runnable for moving creeps.
 * TODO: Interpolation with pathfinding
 * @author clarence
 */
public class MoveCreep implements Runnable {

    private RegCreepControl cc;
    private String baseCoords;
    private BoundingVolume baseBounds;
    private Vector3f curVec;
    private float moveAmount;
    private Vector3f baseCenter;
    private Vector3f vecThreshold;
    private DecimalFormat numFormatter;


    public MoveCreep(RegCreepControl cc) {
        this.cc = cc;
        this.baseCoords = cc.getFormattedBaseCoords();
        this.baseBounds = cc.getBaseBounds();
        this.moveAmount = 0.1f;
        this.vecThreshold = new Vector3f(0.2f,0.2f,0.2f);
        this.numFormatter = new DecimalFormat("0.0");
    }

    public void run() {
        if (cc.path == null) {
            cc.path = cc.pathFinder.getPath(cc.getFormattedCoords(), baseCoords);
            setCurVec();
        }
        if(cc.getSpatial().getWorldBound().intersects(baseBounds)) {
            cc.removeCreep(false);
        } else {
            if (!cc.path.getEndReached()) {
                moveByNode();
            } else {
                getNextPath();
                moveByNode();
            }
        }
    }
    
    private void moveByNode() {
        GraphNode nextGraph = cc.getWorldGraph().getNode(cc.path.getNextPathNode());
        float[] nextCoords = nextGraph.getCoordArray();
        Vector3f newLoc = new Vector3f(nextCoords[0], nextCoords[1], 0.1f);
        cc.getSpatial().setLocalTranslation(newLoc);
    }
    
    private void getNextPath() {
        cc.path = cc.pathFinder.getPath(cc.getFormattedCoords(), baseCoords);        

    }
    private String formatRoundNumber(Float value) {
        return numFormatter.format(Math.round(value));
    }
    
    private void setCurVec() {
        curVec = cc.getWorldGraph().getNode(cc.path.getNextPathNode()).getVector3f();
        resetMoveAmount();
    }
    
    private void moveInNode() {
        cc.getSpatial().setLocalTranslation(cc.getCreepLocation().
                interpolate(curVec, moveAmount));
        if (moveAmount < 1) {
            moveAmount += .025f;
        }
    }
    
    private void resetMoveAmount() {
        moveAmount = 0.01f;
    }
    
    private void getIsNodeEnd() {
        float dist = curVec.distance(cc.getCreepLocation());
        if (dist < .1f) {
            setCurVec();
        }
        
    }
}
          /*  if (!cc.path.getEndReached()) {
                getIsNodeEnd();
                moveInNode();
            } else {
                getNextPath();
            } */