package ShortCircuit.Threading;

import ShortCircuit.Controls.TowerControl;
import ShortCircuit.MapXML.Objects.TowerParams;
import ShortCircuit.States.Game.FriendlyState;

/**
 * This class handles the upgrading of a tower/building of a tower
 *
 * @author Connor Rice
 */
public class TowerUpgrade implements Runnable {

    private int cost;
    private float pitch;
    private FriendlyState fs;
    private boolean valid;
    private TowerParams customParams;
    private String type;

    /**
     * Constructor, takes FriendlyState class as input.
     *
     * @param _fs = FriendlyState
     */
    public TowerUpgrade(FriendlyState fs) {
        this.fs = fs;
    }

    /**
     * Determines type of upgrade to be performed, if it is valid, carries out
     * the necessary operations to upgrade the tower.
     */
    public void run() {
        // Determine type of upgrade/validity
        if (customParams == null) {
            type = fs.getTowerList().get(fs.getSelected()).getType();
        } else {
            type = customParams.getType();
        }
        if (type.equals("TowerUnbuilt")) {
            cost = 100;
            type = "1";
            pitch = 0.6f;
            valid = true;
        } else if (type.equals("Tower1")) {
            cost = 50;
            type = "2";
            pitch = 0.8f;
            valid = true;
        } else if (type.equals("Tower2")) {
            cost = 100;
            type = "3";
            pitch = 0.9f;
            valid = true;
        } else if (type.equals("Tower3")) {
            cost = 500;
            type = "4";
            pitch = 1.0f;
            valid = true;
        }

        // Perform upgrade if valid
        if (valid) {
            if (customParams != null) {
                TowerParams tower = customParams;
                TowerControl tc = customParams.getControl();
                tower.setType("Tower" + type);
                tc.addCharges();
                tc.setBuilt();
                tower.setScale(fs.getTowerBuiltSize());
                fs.towerTextureCharged(tower);
            } else {
                if (fs.getPlrBudget() >= cost) {
                    TowerParams tower = fs.getTowerList().get(fs.getSelected());
                    TowerControl tc = tower.getControl();
                    tower.setType("Tower" + type);
                    tc.addCharges();
                    tc.setBuilt();
                    if (type.equals("4")) {
                        fs.incFours();
                    }
                    tower.setScale(fs.getTowerBuiltSize());
                    fs.towerTextureCharged(tc);
                    fs.decPlrBudget(cost);
                    fs.playBuildSound(pitch);
                }
            }
        }
        type = "";
        valid = false;
        customParams = null;
    }

    public void setManualTower(TowerParams customParams) {
        this.customParams = customParams;
    }
}
