package sc.controls;

import sc.states.game.EnemyState;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 * PENDING: Blinking creepspawner and then flood of enemies, simultaneously shut
 * down other spawners (Juliya's idea)
 *
 * @author Connor Rice
 */
public class CreepSpawnerControl extends AbstractControl {

    private EnemyState EnemyState;

    public CreepSpawnerControl(EnemyState _cstate) {
        EnemyState = _cstate;
    }

    /**
     * Decides if this spawner will spawn a creep based on getNextSpawner(). If
     * the index of the spawner is matched with getNextSpawner(), then it spawns
     * a standard creep and goes to the next spawner.
     *
     * @param tpf
     */
    @Override
    protected void controlUpdate(float tpf) {
        if (EnemyState.getNextSpawner() == getIndex()) {
            if (EnemyState.getCreepListSize() < EnemyState.getNumCreepsByLevel()) {
                EnemyState.spawnRegCreep(spatial.getLocalTranslation());
                EnemyState.goToNextSpawner();
            }
        }
    }

    /**
     * Returns the index of the creepspawner.
     */
    protected int getIndex() {
        return spatial.getUserData("Index");
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        CreepSpawnerControl control = new CreepSpawnerControl(EnemyState);
        return control;
    }
}
