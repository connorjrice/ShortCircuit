package ShortCircuit.Controls;

import ShortCircuit.Objects.STC;
import ShortCircuit.Objects.STCCreepCompare;
import ShortCircuit.Objects.Charges;
import ShortCircuit.States.Game.FriendlyState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Control for user-controlled towers. 
 * @author Connor Rice
 */
public class TowerControl extends AbstractControl {

    public STC<Spatial> reachable;
    public ArrayList<Charges> charges = new ArrayList<Charges>();
    protected FriendlyState FriendlyState;
    private boolean isActive = false;
    private float searchTimer = .0f;
    private float searchDelay = .2f;
    private Comparator<Spatial> cc;
    private Future future;
    private boolean isGlobbed = false;
    private float beamwidth;

    public TowerControl() {
    }

    public TowerControl(FriendlyState _tstate, Vector3f towerloc) {
        FriendlyState = _tstate;
        cc = new STCCreepCompare(towerloc);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (FriendlyState.isEnabled() && isActive) {
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
            setTowerEmpty();
        }

    }

    public void globTower() {
        setInactive();
        FriendlyState.setTowerGlobbedStatus(getIndex(), true);
    }



    public void unglobTower() {
        if (!spatial.getUserData("Type").equals("TowerUnbuilt")) {
            setActive();
        }
        FriendlyState.setTowerGlobbedStatus(getIndex(), false);
    }

    public boolean getIsGlobbed() {
        return isGlobbed;
    }

    private void searchForCreeps() {
        try {
            if (reachable == null && future == null) {
                future = FriendlyState.getEx().submit(callableCreepFind);
            } else if (future != null) {
                if (future.isDone()) {
                    reachable = (STC<Spatial>) future.get();
                    future = null;
                } else if (future.isCancelled()) {
                    future = null;
                }
            }
        } catch (Exception excpt) {
            System.out.println("TowerControl.searchForCreeps()" + 
                    excpt.getLocalizedMessage());
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
    private Callable<STC<Spatial>> callableCreepFind = 
            new Callable<STC<Spatial>>() {
        public STC<Spatial> call() throws Exception {
            STC<Spatial> reach = new STC<Spatial>(cc);
            ArrayList<Spatial> creepClone = FriendlyState.getApp()
                    .enqueue(new Callable<ArrayList<Spatial>>() {
                public ArrayList<Spatial> call() throws Exception {
                    return (ArrayList<Spatial>) FriendlyState.getCreepList()
                            .clone();
                }
            }).get();

            for (int i = 0; i < creepClone.size(); i++) {
                if (creepClone.get(i).getLocalTranslation()
                        .distance(spatial.getLocalTranslation()) < 5.0f) {
                    reach.offer(creepClone.get(i));
                }

            }

            return reach;

        }
    };

    protected void setTowerEmpty() {
        setInactive();
        FriendlyState.addEmptyTower(this);
    }

    protected void shootCreep() {
        if (charges.get(0).getRemBeams() > 0) {
            if (reachable.peek().getControl(RegCreepControl.class) != null) {
                FriendlyState.makeLaserBeam(spatial.getLocalTranslation(), 
                        reachable.peek().getLocalTranslation(),
                        getTowerType(), getBeamWidth());
                if (reachable.peek()
                        .getControl(RegCreepControl.class)
                        .decCreepHealth(charges.get(0).shoot()) <= 0) {
                    reachable.remove();
                }
            } else {
                reachable.remove();
            }
        } else {
            charges.remove(0);
        }
    }

    public void setActive() {
        isActive = true;
    }

    public void setInactive() {
        isActive = false;
    }

    public void setBeamWidth() {
        this.beamwidth = spatial.getUserData("BeamWidth");
    }

    public float getBeamWidth() {
        return beamwidth;
    }

    public int getIndex() {
        return spatial.getUserData("Index");
    }

    public boolean getIsActive() {
        return isActive;
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

    public void addCharges() {
        charges.add(new Charges(getTowerType()));
        if (!isGlobbed) {
            setActive();
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        TowerControl control = new TowerControl(FriendlyState,
                spatial.getLocalTranslation());
        control.setSpatial(spatial);
        return control;
    }
}
