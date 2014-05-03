package ShortCircuit.Tower.Threading;


import ShortCircuit.Tower.MapXML.Objects.TowerParams;
import ShortCircuit.Tower.States.Game.FriendlyState;

/**
 * This class handles the downgrading of a tower from enemy attack.
 *
 * @author Connor Rice
 */
public class TowerDowngrade implements Runnable {

    private String matLoc;
    private FriendlyState fs;
    private boolean valid;
    private int victimIndex;

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
        if (victimIndex != -1) {
            String type = fs.getTowerList().get(victimIndex).getType();
            if (type.equals("Tower1")) {
                type = "Unbuilt";
                matLoc = "Materials/" + fs.getMatDir() + "/TowerUnbuilt.j3m";
                valid = true;
            } else if (type.equals("Tower2")) {
                type = "1";
                matLoc = "Materials/" + fs.getMatDir() + "/Tower1.j3m";
                valid = true;
            } else if (type.equals("Tower3")) {
                type = "2";
                matLoc = "Materials/" + fs.getMatDir() + "/Tower2.j3m";
                valid = true;
            } else if (type.equals("Tower4")) {
                type = "3";
                matLoc = "Materials/" + fs.getMatDir() + "/Tower3.j3m";
                valid = true;
            }

            if (valid) {
                TowerParams tower = fs.getTowerList().get(victimIndex);
                tower.setType("Tower" + type);
                if (type.equals("Unbuilt")) {
                    tower.getControl().charges.clear();
                    tower.getControl().setUnbuilt();
                    tower.setScale(fs.getTowerUnbuiltSize());
                } else {
                    int oldnumcharges = tower.getControl().charges.size();
                    tower.getControl().charges.clear();
                    for (int i = 0; i < oldnumcharges; i++) {
                        tower.getControl().addCharges();
                    }
                    tower.setScale(fs.getTowerBuiltSize());
                }
                tower.getSpatial().setMaterial(fs.getAssetManager().loadMaterial(matLoc));

                valid = false;
                victimIndex = -1;
            }
        }
    }

    public void setVictim(int index) {
        victimIndex = index;
    }
}
