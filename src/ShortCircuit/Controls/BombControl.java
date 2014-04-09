package ShortCircuit.Controls;

import ShortCircuit.States.Game.GameState;
import ShortCircuit.Threading.FindBombVictims;
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
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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
    private int bombDMG = 200;
    private ArrayList<Spatial> reachable;
    private Future future;
    private GameState gs;
    private ScheduledThreadPoolExecutor ex;
    
    public BombControl(float size, GameState _gstate) {
        bombSize = size;
        gs = _gstate;
        this.ex = gs.getEx();
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        if (gs.isEnabled() && bombTimer < .5f && bombSize < 3.0f) {
            bombSize += tpf+.01f;
            spatial.setLocalScale(bombSize);
            bombTimer += tpf;
            if (collideTimer > .03f) {
                collideWithCreeps();
                reachable = null;
                searchForVictims();
                collideTimer = 0;
            }
            else {
                collideTimer += tpf;
            }
        }
        else {
            System.out.println(bombSize);
            spatial.removeFromParent();
            spatial.removeControl(this);
        }
    }
    
    private void searchForVictims() {
        try {
            if (reachable == null && future == null) {
                future = ex.submit(callableFindVics);
            } else if (future != null) {
                if (future.isDone()) {
                    FindBombVictims find = (FindBombVictims) future.get();
                    reachable = find.getCreepVictims();
                    future = null;
                } else if (future.isCancelled()) {
                    future = null;
                }
            }
        } catch (Exception excpt) {
            System.out.println("copout");
        }
    }
    
    
    private Callable<FindBombVictims> callableFindVics = new Callable<FindBombVictims>() {
        public FindBombVictims call() throws Exception {
            ArrayList<Spatial> reach = new ArrayList<Spatial>();
            ArrayList<Spatial> creepClone = gs.getApp().enqueue(new Callable<ArrayList<Spatial>>() {
                public ArrayList<Spatial> call() throws Exception {
                    return (ArrayList<Spatial>) gs.getCreepList().clone();
                }
            }).get();

            for (int i = 0; i < creepClone.size(); i++) {
                if (spatial.getWorldBound().intersects(creepClone.get(i).getWorldBound())) {
                    reach.add(creepClone.get(i));
                }
            }
            return new FindBombVictims(reach);

        }
    };
    /**
     * This method collides with all of the creeps currently.
     * Test method: collide with creepNode instead
     */
    protected void collideWithCreeps() {
        if (reachable != null) {
            for (int i = 0; i < reachable.size(); i++) {
                if (reachable.get(i).getControl(CreepControl.class) != null) {
                    reachable.get(i).getControl(CreepControl.class).decCreepHealth(bombDMG);
                }
                reachable.remove(i);
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
        BombControl control = new BombControl(.1f, gs);
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
