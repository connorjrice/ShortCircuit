package ShortCircuit.Tower.Controls;

import ShortCircuit.Tower.States.Game.HelperState;
import com.jme3.asset.AssetManager;
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
 * Control for Charger NPC Type.
 * The Charger is a player friendly NPC that will charge towers. It must be 
 * purchased, and only contains 10 charges. After it has charged ten towers, it
 * disappears.
 * @author Connor
 */
public class ChargerControl extends AbstractControl {
    private HelperState HelperState;
    private TowerControl destTower;
    private boolean isHome;
    private float moveamount;
    private AssetManager assetManager;

    public ChargerControl(HelperState _hs) {
        HelperState = _hs;
        assetManager = HelperState.getAssetManager();
        isHome = true;
        moveamount = .004f;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        if (destTower != null) {
            if (!spatial.getWorldBound().intersects(destTower.getSpatial().getWorldBound())) {
                spatial.setLocalTranslation(spatial.getLocalTranslation().interpolate(destTower.getSpatial().getWorldBound().getCenter(), .004f));
            }
            else {
                destTower.addCharges();
                destTower.getSpatial().setMaterial(assetManager.loadMaterial("Materials/"+ HelperState.getMatDir() + "/"+ destTower.getTowerType()+".j3m"));
                destTower = null;
            }
        }
        if (destTower == null && isHome == false) {
            if (!spatial.getLocalTranslation().equals(HelperState.getHomeVec())) {
                moveamount += .00003f;
                spatial.setLocalTranslation(spatial.getLocalTranslation().interpolate(HelperState.getHomeVec(), moveamount));
            }
            else {
                isHome = true;
            }
        }
    }

    public void chargeTower(Spatial tower) {
        isHome = false;
        destTower = tower.getControl(TowerControl.class);
    }
    
    public boolean getIsHome() {
        return isHome;
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        ChargerControl control = new ChargerControl(HelperState);
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
