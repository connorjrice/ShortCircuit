package ShortCircuit.Threading;

import ShortCircuit.Controls.RegCreepControl;
import DataStructures.Nodes.GraphNode;
import com.jme3.math.Vector3f;

/**
 * Runnable for moving creeps.
 *
 * @author Connor Rice
 */
public class MoveCreep implements Runnable {

    private RegCreepControl cc;
    private String baseCoords;
    private Vector3f curVec;
    private float moveAmount;
    private int curIndex;
    private int prevIndex;
    private Vector3f prevLoc;
    private Vector3f baseVec;

    public MoveCreep(RegCreepControl cc) {
        this.cc = cc;
        this.baseVec = cc.baseVec;
        this.baseCoords = baseVec.x+","+baseVec.y;
        this.moveAmount = 0.1f;
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

    private void setCurVec() {
        prevIndex = curIndex;
        prevLoc = curVec;
        GraphNode currentNode = cc.getWorldGraph().getNode(cc.path.getNextPathNode());
        curVec = currentNode.getVector3f();
        curIndex = currentNode.getIndex();

        resetMoveAmount();
    }

    private void moveInNode() {
        cc.getSpatial().setLocalTranslation(cc.getCreepLocation().
                interpolate(curVec, moveAmount));
        incMoveAmount();
    }

    private void latchOnNode() {
        if (prevLoc != null) {
            if (cc.getWorldGraph().isEdge(curIndex, prevIndex)) {
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