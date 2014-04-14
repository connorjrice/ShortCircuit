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
    private float moveamount;

    public MoveCreep(CreepState _cs, STDCreepControl _cc) {
        cs = _cs;
        cc = _cc;
        moveamount = .004f;
    }
    
    public void run() {
        if (cc.getSpatial().getWorldBound().intersects(cs.getBaseBounds())) {
            cs.decPlrHealth(cc.getValue());
            cs.creepList.remove(cc.getSpatial());
            cc.getSpatial().removeFromParent();
            cc.getSpatial().removeControl(cc);
        }
        // Pathfinding will go here
        else {
            
            /*Vector3f baselocation = cs.getBaseBounds().getCenter();
            Vector3f creeplocation = cc.getCreepLocation();
            Vector3f interpolated = creeplocation.interpolate(baselocation, moveamount);*/
            //System.out.println(interpolated);
            moveamount += cc.getCreepSpeed();
            cc.getSpatial().setLocalTranslation(cc.getCreepLocation().interpolate(cs.getBaseBounds().getCenter(), moveamount));
        }
    }
    
}
