package ShortCircuit.Tower.Threading;

import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.States.Game.FriendlyState;

/**
 * This class handles the upgrading of a tower/building of a tower
 *
 * @author Connor Rice
 */
public class TowerCharge implements Runnable {

    private final int chargeCost = 10;
    private FriendlyState fs;
    private TowerControl tower;

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
        if (fs.getPlrBudget() >= chargeCost && !tower.getTowerType().equals("TowerUnbuilt")) {
            fs.changeTowerTexture(tower, tower.getTowerType());
            tower.addCharges();
            fs.decPlrBudget(chargeCost);
            fs.playChargeSound();
            tower = null;
        }

    }

    public void setTower(TowerControl _tower) {
        tower = _tower;
    }
}
