package ShortCircuit.Transit.Controls;

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
 * PENDING: Implement player control for Transit
 * @author Connor
 */
public class PlayerControl extends AbstractControl {
    
    @Override
    protected void controlUpdate(float tpf) {
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
    
    public Control cloneForSpatial(Spatial spatial) {
        PlayerControl control = new PlayerControl();
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
