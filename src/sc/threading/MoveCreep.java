package sc.threading;

import sc.controls.RegCreepControl;
import DataStructures.Nodes.GraphNode;
import com.jme3.math.Vector3f;

/**
 * Runnable for moving creeps.
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

    private void getNextPath() {
        cc.path = cc.pathFinder.getPath(cc.getFormattedCoords(), baseCoords);
        setCurVec();
    }

    private void setCurVec() {
        prevIndex = curIndex;
        prevLoc = curVec;
        GraphNode currentNode = cc.getWorldGraph().getNode(cc.path
                .getNextPathNode());
        curVec = getVector3f(currentNode.getElement());
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
    
    public Vector3f getVector3f(Comparable element) {
        String[] strArr = ((String) element).split(",");
        return new Vector3f(Float.parseFloat(strArr[0]), Float.parseFloat(strArr[1]), 0.1f);
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