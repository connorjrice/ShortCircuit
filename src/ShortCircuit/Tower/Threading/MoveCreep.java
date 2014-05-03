package ShortCircuit.Tower.Threading;

import ShortCircuit.Tower.Controls.RegCreepControl;
import ShortCircuit.Tower.States.Game.EnemyState;

/**
 * Runnable for moving creeps.
 * @author clarence
 */
public class MoveCreep implements Runnable {

    private EnemyState es;
    private RegCreepControl cc;
    private float moveamount;

    public MoveCreep(EnemyState es, RegCreepControl cc) {
        this.es = es;
        this.cc = cc;
        moveamount = .004f;
        ///TODO: Make moveamount something more modifiable.
    }

    public void run() {
        if (cc.getSpatial().getWorldBound().intersects(es.getBaseBounds())) {
            es.decPlrHealth(cc.getValue());
            es.creepList.remove(cc.getSpatial());
            cc.getSpatial().removeFromParent();
            cc.getSpatial().removeControl(cc);
        } else {
            /*
             * Pathfinding based upon interpolation.
             */
            moveamount += cc.getCreepSpeed();
            cc.getSpatial().setLocalTranslation(cc.getCreepLocation().
                    interpolate(es.getBaseBounds().getCenter(), moveamount));
        }
    }
}
