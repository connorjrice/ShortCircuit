package ShortCircuit.Threading;

import ShortCircuit.Objects.Charges;
import ShortCircuit.Controls.TowerControl;
import ShortCircuit.States.Game.GameState;
import com.jme3.asset.AssetManager;

/**
 * This class handles the upgrading of a tower/building of a tower
 * TODO: This isn't actually threaded. Make it so.
 * @author Connor Rice
 */
public class UpgradeTower implements Runnable {
    private GameState gs;
    private AssetManager assetManager;
    private int cost;
    private String type;
    private String matLoc;

    public UpgradeTower(GameState _gs, Object _type) {
        gs = _gs;
        type = (String) _type;
        this.assetManager = gs.getAssetManager();
        getUpgradeType();
    }
    
    /**
     * Determines the cost, name, and location of the texture of the next
     * available tower upgrade. ANGRYMONSTER ensures that you cannot go beyond
     * a purple tower.
     */
    private void getUpgradeType() {
        if (type.equals("unbuilt")) {
            cost = 100;
            type = "redLaser";
            matLoc = "Materials/Tower1.j3m";
        }
        else if (type.equals("redLaser")) {
            cost = 50;
            type = "pinkLaser";
            matLoc = "Materials/Tower2.j3m";
        }
        else if (type.equals("pinkLaser")) {
            cost = 100;
            type = "greenLaser";
            matLoc = "Materials/Tower3.j3m";
        }
        else if (type.equals("greenLaser")) {
            cost = 500;
            type = "purpleLaser";
            matLoc = "Materials/Tower4.j3m";
        }
        else if (type.equals("purpleLaser")) {
            type = "ANGRYMONSTER";
        }
    }
    
    /**
     * Checks to see if selected is actually a tower, also makes sure the tower
     * is upgradeable.
     * Mostly uses methods from GameState.
     * URGENT: Make other threadable classes as beautiful as this one
     */
    public void run() {
        if (gs.getSelected() != -1) {
            if (!type.equals("ANGRYMONSTER")) {
                TowerControl tower = gs.getTowerList().get(gs.getSelected()).getControl(TowerControl.class);
                if (gs.getPlrBudget() >= cost) {
                    tower.charges.add(new Charges(type));
                    tower.setType(type);
                    tower.getSpatial().setMaterial(assetManager.loadMaterial(matLoc));
                    tower.setSize(gs.getTowerState().getBuiltTowerSize());
                    gs.decPlrBudget(cost);
                } 
            }
        }
    }

}
