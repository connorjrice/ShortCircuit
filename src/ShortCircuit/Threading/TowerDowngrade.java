package ShortCircuit.Threading;


import ShortCircuit.Controls.TowerControl;
import ShortCircuit.States.Game.FriendlyState;
import com.jme3.scene.Spatial;

/**
 * This class handles the downgrading of a tower from enemy attack.
 * TODO: Finalize use of TowerDowngrade.
 * @author Connor Rice
 */
public class TowerDowngrade implements Runnable {
    
    private int cost;
    private float pitch;
    private FriendlyState fs;
    private boolean valid;
    private TowerControl tower;
    private String type;

    /**
     * Constructor, takes FriendlyState class as input.
     *
     * @param fs = FriendlyState
     */
    public TowerDowngrade(FriendlyState fs) {
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
        if (type.equals("Tower1")) {
            type = "Unbuilt";
            pitch = 0.0f;
            valid = true;
        } else if (type.equals("Tower2")) {
            type = "1";
            pitch = 0.6f;
            valid = true;
        } else if (type.equals("Tower2")) {
            type = "2";
            pitch = 0.8f;
            valid = true;
        } else if (type.equals("Tower4")) {
            type = "3";
            pitch = 0.9f;
            valid = true;
        }

        // Perform upgrade if valid
        if (valid) {
            if (tower != null) {
                tower.setTowerType("Tower" + type);
                if (type.equals("Unbuilt")) {
                    tower.setInactive();
                    tower.getSpatial().setLocalScale(fs.getTowerUnbuiltSize());
                } else {
                    tower.getSpatial().setLocalScale(fs.getTowerBuiltSize());                    
                }
                fs.towerTextureCharged(tower.getSpatial());
            } else {
                tower = fs.getTower(fs.getSelected())
                        .getControl(TowerControl.class);
                if (fs.getPlrBudget() >= cost) {
                    tower.setTowerType("Tower" + type);
                    if (type.equals("3")) {
                        fs.decFours();
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
}
