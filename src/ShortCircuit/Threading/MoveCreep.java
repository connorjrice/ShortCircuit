package ShortCircuit.Threading;

import ShortCircuit.Controls.CreepControl;
import ShortCircuit.States.Game.CreepState;

/**
 * Currently, this class moves creeps based upon a Vector3f called direction.
 * Eventually, this will be used to do pathfinding!
 * @author clarence
 */
public class MoveCreep {
    private CreepState cs;
    private CreepControl cc;
    
    public MoveCreep(CreepState _cs, CreepControl _cc) {
        cs = _cs;
        cc = _cc;
    }
    
    /**
     * This takes care of the death conditions, and moves the creep in the
     * direction specified by it's direction.
     */
    public void run() {
        if (cc.getSpatial().getWorldBound().intersects(cs.getBaseBounds())) {
            cs.decPlrHealth(cc.getValue());
            cs.creepList.remove(cc.getSpatial());
            cc.getSpatial().removeFromParent();
            cc.getSpatial().removeControl(cc);
        }
        else if (cc.getCreepHealth() <= 0) {
            cs.incPlrBudget(cc.getValue());
            cs.incPlrScore(1);
            cs.creepList.remove(cc.getSpatial());
            cc.getSpatial().removeFromParent();
            cc.getSpatial().removeControl(cc);
        }
        // Pathfinding will go here
        else {
            cc.getSpatial().move(cc.getDirection());
        }
    }
    
}
