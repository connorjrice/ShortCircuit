package ShortCircuit.Controls;

import ShortCircuit.Threading.FindCreeps;
import ShortCircuit.DataStructures.STC;
import ShortCircuit.DataStructures.STCCreepCompare;
import ShortCircuit.States.Game.TowerState;
import ShortCircuit.States.Game.BeamState;
import ShortCircuit.Objects.Charges;
import com.jme3.audio.AudioNode;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * @author Connor Rice
 */
public class TowerControl extends AbstractControl {

    protected BeamState BeamState;
    public int[] reachableSpawners;
    public STC<Spatial> reachable;
    public ArrayList<Charges> charges = new ArrayList<Charges>();
    protected FindCreeps findCreeps;
    private Vector3f towerloc;
    protected TowerState TowerState;
    private ScheduledThreadPoolExecutor ex;
    private boolean isBuilt;
    private float shotTimer = 0;
    private float shotDelay = .3f;
    private float searchTimer = .3f;
    private float searchDelay = .3f;
    private float genDelay = 0;
    private int[] allowedSpawners;
    private Comparator<Spatial> cc;
    private Future future;
    private AudioNode emptySound;

    public TowerControl(BeamState _bstate, TowerState _tstate, Vector3f loc, boolean enabled) {
        BeamState = _bstate;
        TowerState = _tstate;
        towerloc = loc;
        cc = new STCCreepCompare(towerloc);
        reachable = new STC<Spatial>(cc);
        isBuilt = enabled;
        this.ex = TowerState.getEx();
        emptySound = new AudioNode(TowerState.getApp().getAssetManager(), "Audio/emptytower.wav");
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (TowerState.isEnabled() && isBuilt) {
            if (genDelay > .4f) {
                if (!charges.isEmpty()) {
                    if (searchTimer > searchDelay) {
                        reachable = null;
                        searchForCreeps();
                        searchTimer = 0;
                    } else {
                        searchTimer += tpf;
                    }
                    if (shotTimer > shotDelay) {
                        decideShoot();
                        shotTimer = 0;
                    } else {
                        shotTimer += tpf;
                    }
                } else {
                    emptyTower();
                }

            }
            else {
                genDelay += tpf;
            }

        }
    }
    
    

    private void excludeCreeps() {
        ArrayList<Spatial> creepSpawners = TowerState.getCreepSpawnerList();
        allowedSpawners = new int[creepSpawners.size()];
        for (int i = 0; i < creepSpawners.size(); i++) {
            if (creepSpawners.get(i).getLocalTranslation().distance(towerloc) < 10.0f) {
                allowedSpawners[i] = creepSpawners.get(i).getUserData("Parent");
            }
        }
    }

    public int[] getAllowedSpawners() {
        return allowedSpawners;
    }

    private void searchForCreeps() {
        try {
            if (reachable == null && future == null) {
                future = ex.submit(callableCreepFind);
            } else if (future != null) {
                if (future.isDone()) {
                    FindCreeps find = (FindCreeps) future.get();
                    reachable = find.getReach();
                    future = null;
                } else if (future.isCancelled()) {
                    future = null;
                }
            }
        } catch (Exception excpt) {
            System.out.println("copout");
        }
    }
    /**
     * Here we are cloning the entire list of creeps ultimately from GameState.
     * Current goal is to parse this list in a way that fewer offerings are
     * being made.
     *
     * Future goal would be to only build the relevant list of creeps in the
     * first place.
     */
    private Callable<FindCreeps> callableCreepFind = new Callable<FindCreeps>() {
        public FindCreeps call() throws Exception {
            STC<Spatial> reach = new STC<Spatial>(cc);
            ArrayList<Spatial> creepClone = TowerState.getApp().enqueue(new Callable<ArrayList<Spatial>>() {
                public ArrayList<Spatial> call() throws Exception {
                    return (ArrayList<Spatial>) TowerState.getCreepList().clone();
                }
            }).get();

            for (int i = 0; i < creepClone.size(); i++) {
                if (creepClone.get(i).getLocalTranslation().distance(towerloc) < 5.0f) {
                    reach.offer(creepClone.get(i));
                }

            }

            return new FindCreeps(reach);

        }
    };

    protected void decideShoot() {
        if (reachable != null) {
            if (!reachable.isEmpty()) {
                shootCreep();
            }
        }
    }

    protected void emptyTower() {
        emptySound.play();
        TowerState.changeTowerTexture(TowerState.towEmMatLoc, this);
    }

    protected void shootCreep() {
        if (charges.get(0).getRemBullets() > 0) {
            if (reachable.peek().getControl(CreepControl.class) != null) {
                BeamState.makeLaserBeam(towerloc, reachable.peek().getLocalTranslation(), getType());
                if (reachable.peek().getControl(CreepControl.class).decCreepHealth(charges.get(0).shoot()) <= 0) {
                    reachable.remove();
                }
            } else {
                reachable.remove();
            }
        } else {
            charges.remove(0);
        }
    }

    public void setBuilt() {
        isBuilt = true;
    }

    public void addInitialCharges() {
        charges.add(new Charges("redLaser"));
    }

    public int getHeight() {
        return spatial.getUserData("Height");

    }

    public int getIndex() {
        return spatial.getUserData("Index");

    }

    public String getType() {
        return spatial.getUserData("Type");
    }

    public void setType(String newtype) {
        spatial.setUserData("Type", newtype);
    }

    public void setSize(Vector3f size) {
        spatial.setLocalScale(size);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not yet implemented.");
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
