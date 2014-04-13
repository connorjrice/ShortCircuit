/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Connor
 */
public class GlobControl extends AbstractControl {
    private CreepState cs;
    
    public GlobControl(CreepState _cs) {
        cs = _cs;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (getGlobHealth() <= 0) {
            cs.incPlrBudget(10);
            unglobTower();
            cs.getCreepNode().detachChild(spatial);
            cs.globList.remove(spatial);
            spatial.removeControl(this);
        }
    }
    
    private void disableVictimTower() {
        getVictimTower().getControl(TowerControl.class).disableTower();
    }
    
    protected void enableVictimTower() {
        getVictimTower().getControl(TowerControl.class).enableTower();
    }
    
    protected void unglobTower() {
       getVictimTower().getControl(TowerControl.class).unglobTower();
    }
    
    private Spatial getVictimTower() {
       return cs.getTowerList().get(getVictimTowerInd());
    }
    
    private boolean getIsVictimTowerBuilt() {
        if (cs.getTowerList().get(getVictimTowerInd()).getUserData("Type").equals("unbuilt")) {
            return false;
        }
        else {
            return true;
        }
    }
    
    private int getVictimTowerInd() {
        return spatial.getUserData("TowerIndex");
    }
    
    public int decGlobHealth() {
        int health = spatial.getUserData("Health");
        spatial.setUserData("Health", health-1);
        return health-1;
    }
    
    public int getGlobHealth() {
        return spatial.getUserData("Health");
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        GlobControl control = new GlobControl(cs);
        //TODO: copy parameters to new Control
        return control;
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        //TODO: load properties of this Control, e.g.
        //this.value = in.readFloat("name", defaultValue);
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        //TODO: save properties of this Control, e.g.
        //out.write(this.value, "name", defaultValue);
    }
}