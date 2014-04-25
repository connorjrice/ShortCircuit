package ShortCircuit.Tower.Controls;

import ShortCircuit.Tower.Threading.MoveCreep;
import ShortCircuit.Tower.States.Game.CreepState;
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

    protected CreepState CreepState;
    protected int creepNum;
    protected Vector3f direction;
    private MoveCreep mc;

    /**
     * Initialization for Control Classes happens in Constructors.
     * @param _state - our CreepState
     * We need access to CreepState for certain methods.
     * 
     * TODO: Implement standardized naming of passed states, (CreepState or cs)
     */
    public STDCreepControl(CreepState _state) {
        CreepState = _state;
        mc = new MoveCreep(CreepState, this);
    }

    /**
     * If CreepState is enabled, we create a new MoveCreep runnable to 
     * @param tpf 
     */
    @Override
    protected void controlUpdate(float tpf) {
        if (CreepState.isEnabled()) {
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
            CreepState.incPlrBudget(getValue());
            CreepState.incPlrScore(1);
            CreepState.creepList.remove(getSpatial());
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
        CreepState.creepList.remove(spatial);
        CreepState.incPlrBudget(getValue());
        CreepState.incPlrScore(1);
        spatial.removeFromParent();
        spatial.removeControl(this);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    @Override
    public STDCreepControl cloneForSpatial(Spatial spatial) {
        STDCreepControl control = new STDCreepControl(CreepState);
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
