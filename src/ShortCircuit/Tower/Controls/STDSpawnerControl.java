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
 * PENDING: Blinking creepspawner and then flood of enemies, simultaneously shut
 * down other spawners (Juliya's idea)
 * @author Connor Rice
 */
public class STDSpawnerControl extends AbstractControl {
    
    private CreepState CreepState;

    public STDSpawnerControl(CreepState _cstate) {
        CreepState = _cstate;
    }
    
    /**
     * Decides if this spawner will spawn a creep based on getNextSpawner().
     * If the index of the spawner is matched with getNextSpawner(), then it 
     * spawns a standard creep and goes to the next spawner.
     * @param tpf 
     */
    @Override
    protected void controlUpdate(float tpf) {
        if (CreepState.getNextSpawner() == getIndex()) {
            if (CreepState.getCreepListSize() < CreepState.getNumCreepsByLevel()) {
                CreepState.startSTDCreep(spatial.getLocalTranslation(), getIndex());
                CreepState.goToNextSpawner();
            }
        }
    }
    
    /**
     * Returns the index of the creepspawner.
     * @return 
     */
    protected int getIndex() {
        return spatial.getUserData("Index");
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        STDSpawnerControl control = new STDSpawnerControl(CreepState);
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
