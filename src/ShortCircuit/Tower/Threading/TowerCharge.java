package ShortCircuit.Tower.Threading;

import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.States.Game.TowerState;

/**
 * This class handles the upgrading of a tower/building of a tower
 *
 * @author Connor Rice
 */
public class TowerCharge implements Runnable {

    private final int chargeCost = 10;
    private TowerState ts;
    private TowerControl tower;

    /**
     * Constructor, takes GameState class as input.
     *
     * @param _gs = GameState
     */
    public TowerCharge(TowerState _ts) {
        ts = _ts;
    }

    /**
     * Determines type of upgrade to be performed, if it is valid, carries out
     * the necessary operations to upgrade the tower.
     */
    public void run() {
        if (ts.getPlrBudget() >= chargeCost && !tower.getTowerType().equals("TowerUnbuilt")) {
            ts.changeTowerTexture("Materials/" + ts.getMatDir() + "/" + tower.getTowerType() + ".j3m", tower);
            tower.addCharges();
            ts.decPlrBudget(chargeCost);
            ts.playChargeSound();
            tower = null;
        }

    }

    public void setTower(TowerControl _tower) {
        tower = _tower;
    }
}
