package ShortCircuit.Controls;

import ShortCircuit.States.Game.EnemyState;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 * The digger enemey will slowly emerge from the ground. It is damageable from
 * the point at which it is shown on the map. It will either be popped in the
 * same style as globs, or it will be affected by bombs. TODO: Decide upon
 * method for damaging the digger enemy.
 *
 * It will be similar to a creep spawner. It should spawn a fair ways away from
 * the base, because otherwise the creeps that come from it will be very danger-
 * ous.
 *
 * @author Connor
 */
public class DiggerControl extends AbstractControl {

    private EnemyState cs;
    private boolean doneDigging = false;
    private final float creepDelay = 3.0f;
    private float creepTimer = 0f;

    public DiggerControl(EnemyState _cs) {
        cs = _cs;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isAlive() && cs.isEnabled()) {
            if (doneDigging) {
            }
        }
    }

    private void removeDigger() {
        //cs. remove digger from list
        spatial.removeFromParent();
        spatial.removeControl(this);
    }

    public boolean isAlive() {
        if (getDiggerHealth() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getDiggerHealth() {
        return spatial.getUserData("Health");
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        DiggerControl control = new DiggerControl(cs);
        return control;
    }
}
