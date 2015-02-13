package sc.controls;

import sc.states.game.EnemyState;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 * This creep will come down from the ceiling and try to destroy one of your
 * towers if you don't get it first
 * @author Connor
 */
public class GlobControl extends AbstractControl {

    private EnemyState cs;
    private static final int BUDGET_VALUE = 10;
    private static final float GEOM_DESCALE = .9f;

    public GlobControl(EnemyState _cs) {
        cs = _cs;
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    public void remove() {
        cs.incPlrBudget(BUDGET_VALUE);
        cs.incPlrScore(BUDGET_VALUE);
        unglobTower();
        cs.getCreepNode().detachChild(spatial);
        cs.getGlobList().remove(spatial);
        spatial.removeControl(this);

    }

    protected void enableVictimTower() {
        getVictimTower().getControl(TowerControl.class).setActive();
    }

    protected void unglobTower() {
        cs.getTowerList().get(getVictimTowerInd())
                .getControl(TowerControl.class).unglobTower();
    }

    protected Spatial getVictimTower() {
        return cs.getTowerList().get(getVictimTowerInd());
    }

    private int getVictimTowerInd() {
        return spatial.getUserData("TowerIndex");
    }

    public int decGlobHealth() {
        spatial.setLocalScale(spatial.getLocalScale().mult(GEOM_DESCALE));
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
}
