package ShortCircuit.Controls;

import ShortCircuit.States.Game.GameState;
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
 * TODO: Documentation
 * TODO: Add input to change amount of damage to creep
 * TODO: Upgrades (textures/effect)
 * @author Connor Rice
 */
public class BombControl extends AbstractControl {
    private float bombTimer = 0f;
    private float collideTimer = 0f;
    private float bombSize;
    private GameState GameState;
    
    public BombControl(float size, GameState _gstate) {
        bombSize = size;
        GameState = _gstate;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        if (bombTimer < .3 && GameState.isEnabled()) {
            bombSize += 0.03f;
            spatial.setLocalScale(bombSize);
            bombTimer += tpf;
            if (collideTimer > .1f) {
                collideWithCreeps();
                collideTimer = 0;
            }
            else {
                collideTimer += tpf;
            }
        }
        else {
            spatial.removeFromParent();
            spatial.removeControl(this);
        }
    }
    
    /**
     * This method collides with all of the creeps currently.
     * Test method: collide with creepNode instead
     */
    protected void collideWithCreeps() {
        for (int i = 0; i < GameState.getCreepList().size(); i++) {
            if (spatial.getWorldBound().intersects(GameState.getCreepList().get(i).getWorldBound())) {
                CreepControl creep = GameState.getCreepList().get(i).getControl(CreepControl.class);
                creep.decCreepHealth(50);
            }
        }
    }
    
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        BombControl control = new BombControl(.1f, GameState);
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
