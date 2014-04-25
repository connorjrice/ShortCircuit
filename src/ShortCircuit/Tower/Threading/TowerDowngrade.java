package ShortCircuit.Tower.Threading;

import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.States.Game.TowerState;

/**
 * This class handles the downgrading of a tower from enemy attack.
 *
 * @author Connor Rice
 */
public class TowerDowngrade implements Runnable {

    private String matLoc;
    private TowerState ts;
    private boolean valid;
    private int victimIndex;

    /**
     * Constructor, takes TowerState class as input.
     *
     * @param _ts = TowerState
     */
    public TowerDowngrade(TowerState _ts) {
        ts = _ts;
    }

    /**
     * Determines type of upgrade to be performed, if it is valid, carries out
     * the necessary operations to upgrade the tower.
     */
    public void run() {
        // Determine type of upgrade/validity
        if (victimIndex != -1) {
            String type = ts.getTowerList().get(victimIndex).getUserData("Type");
            if (type.equals("Tower1")) {
                type = "Unbuilt";
                matLoc = "Materials/" + ts.getMatDir() + "/TowerUnbuilt.j3m";
                valid = true;
            } else if (type.equals("Tower2")) {
                type = "1";
                matLoc = "Materials/" + ts.getMatDir() + "/Tower1.j3m";
                valid = true;
            } else if (type.equals("Tower3")) {
                type = "2";
                matLoc = "Materials/" + ts.getMatDir() + "/Tower2.j3m";
                valid = true;
            } else if (type.equals("Tower4")) {
                type = "3";
                matLoc = "Materials/" + ts.getMatDir() + "/Tower3.j3m";
                valid = true;
            }

            if (valid) {
                TowerControl tower = ts.getTowerList().get(victimIndex).getControl(TowerControl.class);
                tower.setTowerType("Tower" + type);
                tower.setBeamType("beam" + type);
                if (type.equals("Unbuilt")) {
                    tower.charges.clear();
                    tower.setUnbuilt();
                    tower.setSize(ts.getUnbuiltTowerSize());
                } else {
                    int oldnumcharges = tower.charges.size();
                    tower.charges.clear();
                    for (int i = 0; i < oldnumcharges; i++) {
                        tower.addCharges();
                    }
                    tower.getSpatial().setMaterial(ts.getAssetManager().loadMaterial(matLoc));
                    tower.setSize(ts.getBuiltTowerSize());
                }


                valid = false;
                victimIndex = -1;
            }
        }
    }

    public void setVictim(int index) {
        victimIndex = index;
    }
}
