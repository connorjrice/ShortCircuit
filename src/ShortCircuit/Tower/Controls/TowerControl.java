package ShortCircuit.Tower.Controls;

import ShortCircuit.DataStructures.STC;
import ShortCircuit.DataStructures.STCCreepCompare;
import ShortCircuit.Tower.States.Game.TowerState;
import ShortCircuit.Tower.States.Game.BeamState;
import ShortCircuit.Tower.Objects.Charges;
import ShortCircuit.Tower.States.Game.HelperState;
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
 * Control for user-controlled towers.
 * TODO: Documentation for TowerControl
 * @author Connor Rice
 */
public class TowerControl extends AbstractControl {

    protected BeamState BeamState;
    public int[] reachableSpawners;
    public STC<Spatial> reachable;
    public ArrayList<Charges> charges = new ArrayList<Charges>();
    private Vector3f towerloc;
    protected TowerState TowerState;
    private ScheduledThreadPoolExecutor ex;
    private boolean isActive = false;
    private float searchTimer = .0f;
    private float searchDelay = .2f;
    private int[] allowedSpawners;
    private Comparator<Spatial> cc;
    private Future future;
    private AudioNode emptySound;
    private boolean isGlobbed = false;
    private HelperState HelperState;

    public TowerControl(TowerState _tstate, Vector3f loc) {
        TowerState = _tstate;
        BeamState = TowerState.getApp().getStateManager().getState(BeamState.class);
        HelperState = TowerState.getApp().getStateManager().getState(HelperState.class);
        towerloc = loc;
        cc = new STCCreepCompare(towerloc);
        reachable = new STC<Spatial>(cc);
        this.ex = TowerState.getEx();
        emptySound = new AudioNode(TowerState.getApp().getAssetManager(), "Audio/emptytower.wav");
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (TowerState.isEnabled() && isActive) {
            if (searchTimer > searchDelay) {
                decideShoot();
                reachable = null;
                searchForCreeps();
                searchTimer = 0;
            } else {
                searchTimer += tpf;
            }
        }
    }

    protected void decideShoot() {
        if (!charges.isEmpty()) {
            if (reachable != null) {
                if (!reachable.isEmpty()) {
                    shootCreep();
                }
            }
        } else {
            emptyTower();
        }

    }

    public void disableTower() {
        isActive = false;
    }

    public void globTower() {
        isActive = false;
        TowerState.getGlobbedTowerList().add(getIndex());
        isGlobbed = true;
    }

    public void enableTower() {
        if (!spatial.getUserData("Type").equals("UnbuiltTower")) {
            isActive = true;
        }
    }

    public void unglobTower() {
        if (!spatial.getUserData("Type").equals("UnbuiltTower")) {
            isActive = true;
        }
        isGlobbed = false;
        if (TowerState.getGlobbedTowerList().indexOf(getIndex()) != -1) {
            TowerState.getGlobbedTowerList().remove(TowerState.getGlobbedTowerList().indexOf(getIndex()));
        }
    }

    public ArrayList<Integer> getGlobbedTowerIndices() {
        return TowerState.getGlobbedTowerList();
    }

    public boolean getIsGlobbed() {
        return isGlobbed;
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
                    reachable = (STC<Spatial>) future.get();
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
    private Callable<STC<Spatial>> callableCreepFind = new Callable<STC<Spatial>>() {
        public STC<Spatial> call() throws Exception {
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

            return reach;

        }
    };

    protected void emptyTower() {
        emptySound.play();
        TowerState.changeTowerTexture(TowerState.towEmMatLoc, this);
        HelperState.addEmptyTower(spatial);
    }

    protected void shootCreep() {
        if (charges.get(0).getRemBeams() > 0) {
            if (reachable.peek().getControl(STDCreepControl.class) != null) {
                BeamState.makeLaserBeam(towerloc, reachable.peek().getLocalTranslation(), getTowerType(), getBeamType(), getBeamWidth());
                if (reachable.peek()
                        .getControl(STDCreepControl.class).decCreepHealth(charges.get(0).shoot()) <= 0) {
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
        isActive = true;
    }

    public void addInitialCharges() {
        charges.add(new Charges("tower1"));
    }

    public float getBeamWidth() {
        return spatial.getUserData("BeamWidth");
    }

    public int getIndex() {
        return spatial.getUserData("Index");

    }

    public String getBeamType() {
        return spatial.getUserData("BeamType");
    }

    public void setBeamType(String newtype) {
        spatial.setUserData("BeamType", newtype);
    }

    public String getTowerType() {
        return spatial.getUserData("Type");
    }

    public void setTowerType(String newtype) {
        spatial.setUserData("Type", newtype);
    }

    public void setSize(Vector3f size) {
        spatial.setLocalScale(size);
    }
    
    public void addCharges() {
        charges.add(new Charges(getTowerType()));
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
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
