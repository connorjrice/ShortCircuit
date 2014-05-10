package ShortCircuit.Threading;

import ShortCircuit.Controls.RegCreepControl;
import ShortCircuit.States.Game.EnemyState;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;

/**
 * Runnable for moving creeps.
 * @author clarence
 */
public class MoveCreep implements Runnable {

    private EnemyState es;
    private RegCreepControl cc;
    private float moveamount;
    private BoundingVolume basebounds;
    private Vector3f basecenter;

    public MoveCreep(EnemyState es, RegCreepControl cc) {
        this.es = es;
        this.cc = cc;
        this.basebounds = es.getBaseBounds();
        this.basecenter = this.basebounds.getCenter();
        moveamount = .004f;
        ///TODO: Make moveamount something more modifiable.
    }

    public void run() {
        if (cc.getSpatial().getWorldBound().intersects(basebounds)) {
            es.decPlrHealth(cc.getValue());
            es.creepList.remove(cc.getSpatial());
            cc.getSpatial().removeFromParent();
            cc.getSpatial().removeControl(cc);
        } else {
            moveamount += cc.getCreepSpeed();
            cc.getSpatial().setLocalTranslation(cc.getCreepLocation().
                    interpolate(basecenter, moveamount));
        }
    }
}
