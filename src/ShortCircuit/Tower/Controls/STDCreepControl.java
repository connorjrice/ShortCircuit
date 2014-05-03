package ShortCircuit.Tower.Controls;

import ShortCircuit.Tower.Threading.MoveCreep;
import ShortCircuit.Tower.States.Game.EnemyState;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.io.IOException;

/**
 * This is the control class for standard creeps.
 * It has not been decided if specialty creeps will inherit this control.
 * Currently, this control is used by the STD family of creeps.
 *
 * @author Connor Rice
 */
public class STDCreepControl extends AbstractControl {

    protected EnemyState EnemyState;
    protected int creepNum;
    protected Vector3f direction;
    private MoveCreep mc;

    public STDCreepControl(EnemyState _state) {
        EnemyState = _state;
        mc = new MoveCreep(EnemyState, this);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (EnemyState.isEnabled()) {
            mc.run();
        }
    }
    public float getCreepSpeed() {
        return spatial.getUserData("Speed");
    }
    
    public int getCreepHealth() {
        return spatial.getUserData("Health");
    }


    /**
     * Decrements creep health, removes creep from world if below 0.
     * @param damage
     * @return 
     */
    public int decCreepHealth(int damage) {
        int health = getCreepHealth();
        spatial.setUserData("Health", health - damage);
        if (health - damage <= 0) {
            EnemyState.incPlrBudget(getValue());
            EnemyState.incPlrScore(1);
            EnemyState.creepList.remove(getSpatial());
            getSpatial().removeFromParent();
            getSpatial().removeControl(this);
        }
        return health - damage;
    }

    public int getValue() {
        return spatial.getUserData("Value");
    }

    public Vector3f getCreepLocation() {
        return spatial.getLocalTranslation();
    }

    public void removeCreep() {
        EnemyState.creepList.remove(spatial);
        EnemyState.incPlrBudget(getValue());
        EnemyState.incPlrScore(1);
        spatial.removeFromParent();
        spatial.removeControl(this);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    @Override
    public STDCreepControl cloneForSpatial(Spatial spatial) {
        STDCreepControl control = new STDCreepControl(EnemyState);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
    }
}
