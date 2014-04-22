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
 * This creep will come down from the ceiling and try to destroy one of your
 * towers if you don't get it first
 *
 * @author Connor
 */
public class GlobControl extends AbstractControl {

    private CreepState cs;

    public GlobControl(CreepState _cs) {
        cs = _cs;
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    public void remove() {
        cs.incPlrBudget(10);
        cs.incPlrScore(10);
        unglobTower();
        cs.getCreepNode().detachChild(spatial);
        cs.getGlobList().remove(spatial);
        spatial.removeControl(this);

    }

    protected void enableVictimTower() {
        getVictimTower().getControl(TowerControl.class).enableTower();
    }

    protected void unglobTower() {
        cs.getTowerList().get(getVictimTowerInd()).getControl(TowerControl.class).unglobTower();
    }
    
    protected Spatial getVictimTower() {
        return cs.getTowerList().get(getVictimTowerInd());
    }


    private int getVictimTowerInd() {
        return spatial.getUserData("TowerIndex");
    }

    public int decGlobHealth() {
        int health = spatial.getUserData("Health");
        spatial.setUserData("Health", health - 1);
        return health - 1;
    }

    public int getGlobHealth() {
        return spatial.getUserData("Health");
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        GlobControl control = new GlobControl(cs);
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
