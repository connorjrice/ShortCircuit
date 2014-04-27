package ShortCircuit.Tower.Threading;

import ShortCircuit.Tower.Controls.STDCreepControl;
import ShortCircuit.Tower.States.Game.CreepState;

/**
 * Runnable for moving creeps.
 * @author clarence
 */
public class MoveCreep implements Runnable {

    private CreepState cs;
    private STDCreepControl cc;
    private float moveamount;

    public MoveCreep(CreepState _cs, STDCreepControl _cc) {
        cs = _cs;
        cc = _cc;
        moveamount = .004f;
        ///TODO: Make moveamount something more modifiable.
    }

    public void run() {
        if (cc.getSpatial().getWorldBound().intersects(cs.getBaseBounds())) {
            cs.decPlrHealth(cc.getValue());
            cs.creepList.remove(cc.getSpatial());
            cc.getSpatial().removeFromParent();
            cc.getSpatial().removeControl(cc);
        } else {
            /*
             * Pathfinding based upon interpolation.
             */
            moveamount += cc.getCreepSpeed();
            cc.getSpatial().setLocalTranslation(cc.getCreepLocation().
                    interpolate(cs.getBaseBounds().getCenter(), moveamount));
        }
    }
}
