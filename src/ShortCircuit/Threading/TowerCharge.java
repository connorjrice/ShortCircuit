package ShortCircuit.Threading;

import ShortCircuit.Controls.TowerControl;
import ShortCircuit.MapXML.TowerParams;
import ShortCircuit.States.Game.FriendlyState;
import com.jme3.scene.Spatial;

/**
 * This class handles the upgrading of a tower/building of a tower
 *
 * @author Connor Rice
 */
public class TowerCharge implements Runnable {

    private int chargeCost = 10;
    private FriendlyState fs;
    private Spatial tp;
    private boolean free;

    /**
     * Constructor, takes GameState class as input.
     *
     * @param _gs = GameState
     */
    public TowerCharge(FriendlyState fs) {
        this.fs = fs;
    }

    /**
     * Determines type of upgrade to be performed, if it is valid, carries out
     * the necessary operations to upgrade the tower.
     */
    public void run() {
        if (free) {
            fs.towerTextureCharged(tp);
            tp.getControl(TowerControl.class).addCharges();
            fs.playChargeSound();
        } else {
            if (fs.getPlrBudget() >= chargeCost && !tp.getUserData("Type").equals("TowerUnbuilt")) {
                fs.towerTextureCharged(tp);
                tp.getControl(TowerControl.class).addCharges();              
                fs.decPlrBudget(chargeCost);
                fs.playChargeSound();

            }
        }
    }

    public void setTower(Spatial _tp, boolean free) {
        tp = _tp;
        this.free = free;
    }
}
