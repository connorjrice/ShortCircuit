package ShortCircuit.Controls;

import ShortCircuit.Threading.MoveCreep;
import ShortCircuit.States.Game.CreepState;
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
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * TODO: Document CreepControl
 *
 * @author Connor Rice
 */
public class CreepControl extends AbstractControl {

    protected CreepState CreepState;
    protected int creepNum;
    protected Vector3f direction;
    protected ScheduledThreadPoolExecutor ex;
    private MoveCreep mc;

    public CreepControl(CreepState _state) {
        CreepState = _state;
        ex = CreepState.getEx();
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (CreepState.isEnabled()) { 
            mc = new MoveCreep(CreepState, this);
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
        return health;
    }

    public int getValue() {
        if (getCreepType().equals("Small")) {
            return 1;
        } else if (getCreepType().equals("Medium")) {
            return 2;
        } else if (getCreepType().equals("Large")) {
            return 3;
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
    public CreepControl cloneForSpatial(Spatial spatial) {
        CreepControl control = new CreepControl(CreepState);
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

            /*if (spatial.getWorldBound().intersects(CreepState.getBaseBounds())) {
                collisionDeath();
            }
            else if (getCreepHealth() <= 0) {
                CreepState.incPlrBudget(getValue());
                CreepState.incPlrScore(1);
                CreepState.creepList.remove(getSpatial());
                getSpatial().removeFromParent();
                getSpatial().removeControl(this);
            }
            else {
                getSpatial().move(direction);
            }*/