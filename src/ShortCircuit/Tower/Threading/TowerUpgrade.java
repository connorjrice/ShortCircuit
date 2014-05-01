package ShortCircuit.Tower.Threading;

import ShortCircuit.Tower.Objects.Game.Charges;
import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.States.Game.FriendlyState;

/**
 * This class handles the upgrading of a tower/building of a tower
 * @author Connor Rice
 */
public class TowerUpgrade implements Runnable {

    private int cost;
    private String matLoc;
    private float pitch;
    private FriendlyState fs;
    private boolean valid;

    /**
     * Constructor, takes FriendlyState class as input.
     * @param _fs = FriendlyState
     */
    public TowerUpgrade(FriendlyState _fs) {
        this.fs = fs;
    }

    /**
     * Determines type of upgrade to be performed, if it is valid, carries out
     * the necessary operations to upgrade the tower.
     */
    public void run() {
        // Determine type of upgrade/validity
        String type = fs.getTowerList().get(fs.getSelected()).getUserData("Type");
        if (type.equals("TowerUnbuilt")) {
            cost = 100;
            type = "1";
            matLoc = "Materials/" + fs.getMatDir() + "/Tower1.j3m";
            pitch = 0.6f;
            valid = true;
        } else if (type.equals("Tower1")) {
            cost = 50;
            type = "2";
            matLoc = "Materials/" + fs.getMatDir() + "/Tower2.j3m";
            pitch = 0.8f;
            valid = true;
        } else if (type.equals("Tower2")) {
            cost = 100;
            type = "3";
            matLoc = "Materials/" + fs.getMatDir() + "/Tower3.j3m";
            pitch = 0.9f;
            valid = true;
        } else if (type.equals("Tower3")) {
            cost = 500;
            type = "4";
            matLoc = "Materials/" + fs.getMatDir() + "/Tower4.j3m";
            pitch = 1.0f;
            valid = true;
        }
        
        // Perform upgrade if valid
        if (fs.getSelected() != -1 && valid) {
            TowerControl tower = fs.getTowerList().get(fs.getSelected()).getControl(TowerControl.class);
            if (fs.getPlrBudget() >= cost) {
                tower.charges.add(new Charges("Tower" + type));
                tower.setBuilt();
                tower.setTowerType("Tower" + type);
                tower.setBeamType("beam" + type);
                if (type.equals("4")) {
                    fs.incFours();
                }
                tower.getSpatial().setMaterial(fs.getAssetManager().loadMaterial(matLoc));
                tower.setSize(fs.getBuiltTowerSize());
                fs.decPlrBudget(cost);
                fs.playBuildSound(pitch);
                valid = false;
            }
        }
    }
}
