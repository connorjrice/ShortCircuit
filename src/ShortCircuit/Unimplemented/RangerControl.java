package ShortCircuit.Unimplemented;

import ShortCircuit.Controls.TowerControl;
import ShortCircuit.States.Game.EnemyState;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * This type of enemy will advance towards a built tower at a slow pace, and
 * attach it's self to the tower. If it is not destroyed within a given time,
 * the tower is destroyed and must be rebuilt.
 *
 * Handling of the selection of a built tower will be handled by EnemyState.
 * Movement will be done using interpolation.
 *
 * The ranger will attach to a tower, which will sound some sort of alarm. Each
 * iteration of the tower will take 3 seconds, so if it is a level 1 tower it
 * will only take 3 seconds to reduce to unbuilt.
 *
 * // find a way to build a grid using distances for these enemies to spawn
 *
 * @author Connor
 */
public class RangerControl extends AbstractControl {

    private EnemyState cs;
    private boolean attachedToTower = false;
    private float downgradeTimer = 0f;
    private final float downgradeDelay = 3.0f;
    private TowerControl victimTower;
    private float moveAmount;

    public RangerControl(EnemyState _cs, TowerControl _vt) {
        cs = _cs;
        victimTower = _vt;
        moveAmount = .04f;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isAlive() && cs.isEnabled()) {
            if (attachedToTower == false) {
                moveTowardsTower();
            } else {
                if (downgradeTimer > downgradeDelay) {
                    downgradeVictimTower();
                    downgradeTimer = 0f;
                } else {
                    downgradeTimer += tpf;
                }
            }
        } else {
            detachRanger();
        }

    }

    private boolean isAlive() {
        if (getRangerHealth() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Moves the ranger spatial towards the victim tower. When the two spatials
     * intersect, the boolean attachedToTower becomes true.
     */
    private void moveTowardsTower() {
        if (!spatial.getWorldBound().intersects(victimTower.getSpatial()
                .getWorldBound())) {
            moveAmount += moveAmount;
            spatial.setLocalTranslation(spatial.getLocalTranslation()
                    .interpolateLocal(victimTower.getSpatial().getLocalTranslation(),
                    moveAmount));
        } else {
            attachedToTower = true;
        }
    }

    private void detachRanger() {
        //remove ranger from list in creep state
        spatial.removeFromParent();
        spatial.removeControl(this);
    }

    private void downgradeVictimTower() {
        TowerControl tower = victimTower;
       // tower.downgrade();
    }

    private int getRangerHealth() {
        return spatial.getUserData("Health");
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    /*public Control cloneForSpatial(Spatial spatial) {
     RangerControl control = new RangerControl(cs, spatial);
     return control;
     }*/

}
