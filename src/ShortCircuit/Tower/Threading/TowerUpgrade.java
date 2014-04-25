package ShortCircuit.Tower.Threading;

import ShortCircuit.Tower.Objects.Charges;
import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.States.Game.TowerState;

/**
 * This class handles the upgrading of a tower/building of a tower
 * @author Connor Rice
 */
public class TowerUpgrade implements Runnable {

    private int cost;
    private String matLoc;
    private float pitch;
    private TowerState ts;
    private boolean valid;

    /**
     * Constructor, takes TowerState class as input.
     * @param _ts = TowerState
     */
    public TowerUpgrade(TowerState _ts) {
        ts = _ts;
    }

    /**
     * Determines type of upgrade to be performed, if it is valid, carries out
     * the necessary operations to upgrade the tower.
     */
    public void run() {
        // Determine type of upgrade/validity
        String type = ts.getTowerList().get(ts.getSelected()).getUserData("Type");
        if (type.equals("TowerUnbuilt")) {
            cost = 100;
            type = "1";
            matLoc = "Materials/" + ts.getMatDir() + "/Tower1.j3m";
            pitch = 0.6f;
            valid = true;
        } else if (type.equals("Tower1")) {
            cost = 50;
            type = "2";
            matLoc = "Materials/" + ts.getMatDir() + "/Tower2.j3m";
            pitch = 0.8f;
            valid = true;
        } else if (type.equals("Tower2")) {
            cost = 100;
            type = "3";
            matLoc = "Materials/" + ts.getMatDir() + "/Tower3.j3m";
            pitch = 0.9f;
            valid = true;
        } else if (type.equals("Tower3")) {
            cost = 500;
            type = "4";
            matLoc = "Materials/" + ts.getMatDir() + "/Tower4.j3m";
            pitch = 1.0f;
            valid = true;
        }
        
        // Perform upgrade if valid
        if (ts.getSelected() != -1 && valid) {
            TowerControl tower = ts.getTowerList().get(ts.getSelected()).getControl(TowerControl.class);
            if (ts.getPlrBudget() >= cost) {
                tower.charges.add(new Charges("Tower" + type));
                tower.setBuilt();
                tower.setTowerType("Tower" + type);
                tower.setBeamType("beam" + type);
                if (type.equals("4")) {
                    ts.incFours();
                }
                tower.getSpatial().setMaterial(ts.getAssetManager().loadMaterial(matLoc));
                tower.setSize(ts.getBuiltTowerSize());
                ts.decPlrBudget(cost);
                ts.playBuildSound(pitch);
                valid = false;
            }
        }
    }
}
