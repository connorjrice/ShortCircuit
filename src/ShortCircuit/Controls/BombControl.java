package ShortCircuit.Controls;

import ShortCircuit.States.Game.AudioState;
import ShortCircuit.States.Game.GraphicsState;
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
 * PENDING: Implementation of upgrades for radius/damage, and creation of
 * textures/ sounds that would go along with that sort of implementation.
 *
 * @author Connor Rice
 */
public class BombControl extends AbstractControl {

    private float bombTimer = 0f;
    private float collideTimer = 0f;
    private float bombSize;
    private int bombDMG = 200;
    private ArrayList<Spatial> reachable;
    private Future future;
    private GraphicsState gs;
    private ScheduledThreadPoolExecutor ex;
    private AudioState as;

    public BombControl(float size, GraphicsState gs, AudioState as) {
        bombSize = size; // Initial size of bomb
        this.gs = gs;    // Game state
        this.as = as;
        this.ex = gs.getEx();
    }

    /**
     * If the gamestate is enabled, and the bomb has been alive for less than
     * .5f, and the bombSize is smaller than 3.0f: 1. Increment bomb size
     * (internally to bombSize variable) 2. Apply change to bomb's spatial 3.
     * Increment bomb timer 4. If enough time has passed to check if we've
     * collided with creeps, we do so. 4a. If not, tpf is added to the collision
     * timer. 5. After we've collided with the creeps, we throw away the
     * reachable we just used. (Reachable is an arraylist of spatials that is
     * given from the callable method "callableFindVics" 6. We then search for
     * victims, and reset the collision timer to 0.
     *
     * @param tpf
     */
    @Override
    protected void controlUpdate(float tpf) {
        if (gs.isEnabled() && bombTimer < .5f && bombSize < 3.0f) {
            bombSize += tpf + .01f;
            spatial.setLocalScale(bombSize);
            bombTimer += tpf;
            if (collideTimer > .03f) {
                collideWithCreeps();
                reachable = null;
                searchForVictims();
                collideTimer = 0;
            } else {
                collideTimer += tpf;
            }
        } /**
         * If any of the ending conditions have been met (3.0f > bombSize, .5f >
         * bombTimer, gs is disabled), we stop the sound, and remove the bomb's
         * spatial, and remove this control.
         */
        else {
            as.stopBombSound();
            spatial.removeFromParent();
            spatial.removeControl(this);
        }
    }

    /**
     * Uses threading to find collisions between the bomb and the creeps. If we
     * don't have a reachable list of creeps, and we don't have a non-null
     * future object, we submit the task of finding the collisions to the
     * executor.
     *
     * If we have a future that is done, we feed that into reachable so it can
     * be utilized in the update loop.
     *
     * If it's cancelled, we nullify future.
     *
     * If something goes wrong, the most lame form of exception handling has
     * been implemented.
     */
    private void searchForVictims() {
        try {
            if (reachable == null && future == null) {
                future = ex.submit(callableFindVics);
            } else if (future != null) {
                if (future.isDone()) {
                    reachable = (ArrayList<Spatial>) future.get();
                    future = null;
                } else if (future.isCancelled()) {
                    future = null;
                }
            }
        } catch (Exception excpt) {
            System.out.println("BombControl.searchForVictims()");
        }
    }
    /**
     * Clones the list of creeps currently on the map, iterates through the list
     * looking for collisions, if there is a collision it is added to the list
     * reach, which is then passed into a FindBombVictims object for retrieval.
     */
    private Callable<ArrayList<Spatial>> callableFindVics = new Callable<ArrayList<Spatial>>() {
        public ArrayList<Spatial> call() throws Exception {
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
            return reach;

        }
    };

    /**
     * If we have a list of reachable creeps that isn't null, we iterate through
     * the list looking for non-null creeps. If we find one, we decrement it's
     * health according to the value of bombDMG.
     */
    protected void collideWithCreeps() {
        if (reachable != null) {
            for (int i = 0; i < reachable.size(); i++) {
                if (reachable.get(i).getControl(RegCreepControl.class) != null) {
                    reachable.get(i).getControl(RegCreepControl.class).decCreepHealth(bombDMG);
                }
                reachable.remove(i);
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        BombControl control = new BombControl(.1f, gs, as);
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
