package ShortCircuit.Threading;

import ShortCircuit.Controls.TowerControl;
import ScSDK.MapXML.TowerParams;
import ShortCircuit.States.Game.FriendlyState;
import com.jme3.scene.Spatial;

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
    private TowerControl tower;
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
        if (tower == null) {
            type = fs.getTowerList().get(fs.getSelected()).getUserData("Type");
        } else {
            type = tower.getTowerType();
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
            if (tower != null) {
                tower.setTowerType("Tower" + type);
                tower.addCharges();
                tower.setBuilt();
                tower.getSpatial().setLocalScale(fs.getTowerBuiltSize());
                fs.towerTextureCharged(tower.getSpatial());
            } else {
                tower = fs.getTower(fs.getSelected()).getControl(TowerControl.class);
                if (fs.getPlrBudget() >= cost) {
                    tower.setTowerType("Tower" + type);
                    tower.addCharges();
                    tower.setBuilt();
                    if (type.equals("4")) {
                        fs.incFours();
                    }
                    tower.getSpatial().setLocalScale(fs.getTowerBuiltSize());
                    fs.towerTextureCharged(tower.getSpatial());
                    fs.decPlrBudget(cost);
                    fs.playBuildSound(pitch);
                }
            }
        }
        type = "";
        valid = false;
        tower = null;
    }

    public void setManualTower(TowerControl tc) {
        this.tower = tc;
    }
}
