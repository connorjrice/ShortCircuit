package ShortCircuit.Threading;

import ShortCircuit.Controls.RegCreepControl;
import ShortCircuit.DataStructures.Nodes.GraphNode;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.text.DecimalFormat;

/**
 * Runnable for moving creeps. TODO: Interpolation with pathfinding
 *
 * @author clarence
 */
public class MoveCreep implements Runnable {

    private RegCreepControl cc;
    private String baseCoords;
    private BoundingVolume baseBounds;
    private Vector3f curVec;
    private float moveAmount;
    private int curIndex;
    private int prevIndex;
    private Vector3f prevLoc;
    
    private DecimalFormat numFormatter;
    private Vector3f baseVec;

    public MoveCreep(RegCreepControl cc, String baseCoords) {
        this.cc = cc;
        this.baseCoords = baseCoords;
        this.baseVec = cc.baseVec;
        this.moveAmount = 0.1f;
        this.numFormatter = new DecimalFormat("0.0");
    }
    

    

    public void run() {
        if (cc.path == null) {
            getNextPath();
        }
        if (cc.getSpatial().getLocalTranslation().distance(baseVec) < 1.0f) {
            cc.removeCreep(false);
        } else {
            if (!cc.path.getEndReached()) {
                if (!getNodeEnd()) {
                    moveInNode();
                } else {
                    latchOnNode();
                }

            } else {
                getNextPath();
                moveInNode();
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
        setCurVec();
    }

    private String formatRoundNumber(Float value) {
        return numFormatter.format(Math.round(value));
    }

    private void setCurVec() {
        prevIndex = curIndex;
        prevLoc = curVec;
        GraphNode currentNode = cc.getWorldGraph().getNode(cc.path.getNextPathNode());
        curVec = currentNode.getVector3f();
        curIndex = currentNode.getIndex();

        resetMoveAmount();
    }
    
    private void setCurVec(GraphNode n) {
        prevIndex = curIndex;
        prevLoc = curVec;
        curVec = n.getVector3f();
        curIndex = n.getIndex();
        
        resetMoveAmount();
    }

    private void moveInNode() {
        cc.getSpatial().setLocalTranslation(cc.getCreepLocation().
                interpolate(curVec, moveAmount));
        incMoveAmount();
    }
    
    /*
     * We'll either 
     */
    private void latchOnNode() {
        if (prevLoc != null) {
            if (cc.getWorldGraph().isEdge(curIndex, prevIndex))  {
                cc.getSpatial().setLocalTranslation(curVec);
                setCurVec();
            } else {
                cc.getSpatial().setLocalTranslation(prevLoc);
                getNextPath();
            }
        } else {
            cc.getSpatial().setLocalTranslation(curVec);
            setCurVec();
        } 
    }
   

    private void incMoveAmount() {
        moveAmount += 0.2f;

    }

    private void resetMoveAmount() {
        moveAmount = 0.1f;
    }

    private boolean getNodeEnd() {
        float dist = curVec.distance(cc.getCreepLocation());
        return dist < 0.05f;
    }
}
          /*  if (!cc.path.getEndReached()) {
                getIsNodeEnd();
                moveInNode();
            } else {
                getNextPath();
            } */