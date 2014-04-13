package ShortCircuit.Tower.Controls;

import ShortCircuit.Tower.States.Game.CreepState;

/**
 * Currently, this class moves creeps based upon a Vector3f called direction.
 * Eventually, this will be used to do pathfinding!
 * @author clarence
 */
public class MoveCreep implements Runnable {
    private CreepState cs;
    private STDCreepControl cc;

    public MoveCreep(CreepState _cs, STDCreepControl _cc) {
        cs = _cs;
        cc = _cc;
    }
    
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
