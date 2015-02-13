package sc.controls;

import sc.states.game.FriendlyState;
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
 * Control for Charger NPC Type. The Charger is a player friendly NPC that will
 * charge towers. It must be purchased, and only contains 10 charges. After it
 * has charged ten towers, it disappears. 
 * @author Connor
 */
public class ChargerControl extends AbstractControl {

    private FriendlyState FriendlyState;
    private TowerControl destTower;
    private boolean isHome;
    private float moveamount;

    public ChargerControl(FriendlyState _fs) {
        FriendlyState = _fs;
        moveamount = .04f;
    }


    @Override
    protected void controlUpdate(float tpf) {
        if (FriendlyState.isEnabled()) {
            nextLocation();
        }
    }

    private void nextLocation() {
        if (FriendlyState.isAnyTowerEmpty() && destTower == null) {
            moveTowardsHome();
        } else {
            if (destTower == null) {
                destTower = FriendlyState.getEmptyTowers().dequeue();
                moveTowardsTower();
            } else {
                moveTowardsTower();
            }
        }
    }

    private void moveTowardsTower() {
        if (!destTower.getIsActive()) {
            if (!spatial.getWorldBound().intersects(destTower.getSpatial()
                    .getWorldBound())) {

                spatial.setLocalTranslation(spatial.getLocalTranslation().
                        interpolate(destTower.getSpatial().
                        getWorldBound().getCenter(), moveamount));
            } else {
                chargeTower();
            }
        } else {
            moveTowardsHome();
        }
    }

    private void chargeTower() {
        FriendlyState.chargeTower(getTowerIndex());
        decRemCharges();
        moveamount = .04f;
        destTower = null;
    }

    private void moveTowardsHome() {
        if (spatial.getWorldBound().distanceTo(FriendlyState.getHomeVec()) > .4f) {
            moveamount += .0003f;
            spatial.setLocalTranslation(spatial.getLocalTranslation()
                    .interpolate(FriendlyState.getHomeVec(), moveamount));
        } else {
            moveamount = .04f;
            setIsHome(true);
        }
    }

    public void startToTower(TowerControl tower) {
        setIsHome(false);
        destTower = tower;
    }

    public boolean getIsHome() {
        return isHome;
    }

    private void setIsHome(boolean is) {
        isHome = is;
    }

    private int getTowerIndex() {
        return destTower.getIndex();
    }

    private void decRemCharges() {
        int old = spatial.getUserData("RemainingCharges");
        if (old == 1) {
            removeCharger();
        } else {
            spatial.setLocalScale(spatial.getLocalScale().mult(.9f));
            spatial.setUserData("RemainingCharges", old - 1);
        }
    }

    private void removeCharger() {
        spatial.removeFromParent();
        spatial.removeControl(this);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        ChargerControl control = new ChargerControl(FriendlyState);
        control.setSpatial(spatial);
        return control;
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        destTower = (TowerControl) in.readSavable("destTower", 
                new TowerControl());
        isHome = in.readBoolean("isHome", false);
        moveamount = in.readFloat("moveAmount", .04f);
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(destTower, "destTower", null);
        out.write(isHome, "isHome", true);
        out.write(moveamount, "moveAmount", .04);
    }
}
