package ShortCircuit.Tower.Controls;

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

    public Vector3f getDirection() {
        return spatial.getUserData("Direction");
    }

    private float getCreepSpeed() {
        return spatial.getUserData("Speed");
    }

    public int getCreepHealth() {
        return spatial.getUserData("Health");
    }

    public String getCreepType() {
        return spatial.getUserData("Type");
    }

    public int decCreepHealth(int damage) {
        int health = getCreepHealth();
        spatial.setUserData("Health", health - damage);
        return health - damage;
    }

    public int getValue() {
        if (getCreepType().equals("Small")) {
            return 1;
        } else if (getCreepType().equals("Medium")) {
            return 2;
        } else if (getCreepType().equals("Large")) {
            return 3;
        } else if (getCreepType().equals("XL")) {
            return 5;
        } else {
            return -1;
        }
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
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
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
