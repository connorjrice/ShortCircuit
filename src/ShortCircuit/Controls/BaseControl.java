package ShortCircuit.Controls;

import ShortCircuit.Objects.Charges;
import ShortCircuit.States.Game.GameState;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.util.ArrayList;
/**
 * PENDING: Make decision as to whether or not the base shooting creeps should
 * be a thing, and then figure out how to implement it. For now, this control
 * is sort of wasting space.
 * @author Connor Rice
 */
public class BaseControl extends AbstractControl {


    protected GameState GameState;
    public ArrayList<Spatial> reachable = new ArrayList<Spatial>();
    public ArrayList<Charges> charges = new ArrayList<Charges>();
    private boolean isActive = false;
    protected Vector3f baseloc;
    protected ColorRGBA tColor;

    public BaseControl(GameState _gstate, Vector3f loc) {
        GameState = _gstate;
        baseloc = loc;
    }

    @Override
    protected void controlUpdate(float tpf) {

    }

    

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        BaseControl control = new BaseControl(GameState, baseloc);
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
