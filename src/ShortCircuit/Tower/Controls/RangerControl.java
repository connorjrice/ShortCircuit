package ShortCircuit.Tower.Controls;

import ShortCircuit.Tower.States.Game.CreepState;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 * Control for Ranger special creep type.
 * @author Connor
 */
public class RangerControl extends AbstractControl {
    
    private CreepState cs;
    
    public RangerControl(CreepState _cs) {
        cs = _cs;
    }
    
    @Override 
    protected void controlUpdate(float tpf) {
        //TODO: add code that controls Spatial,
        //e.g. spatial.rotate(tpf,tpf,tpf);
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        RangerControl control = new RangerControl(cs);
        //TODO: copy parameters to new Control
        return control;
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        //TODO: load properties of this Control, e.g.
        //this.value = in.readFloat("name", defaultValue);
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        //TODO: save properties of this Control, e.g.
        //out.write(this.value, "name", defaultValue);
    }
}
