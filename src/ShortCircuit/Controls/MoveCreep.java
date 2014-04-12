package ShortCircuit.Controls;

import ShortCircuit.Controls.STDCreepControl;
import ShortCircuit.States.Game.CreepState;

/**
 * Currently, this class moves creeps based upon a Vector3f called direction.
 * Eventually, this will be used to do pathfinding!
 * @author clarence
 */
public class MoveCreep {
    /**
     * This takes care of the death conditions, and moves the creep in the
     * direction specified by it's direction.
     */
    public void run(CreepState cs, STDCreepControl cc) {
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
