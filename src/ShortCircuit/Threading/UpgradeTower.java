package ShortCircuit.Threading;

import ShortCircuit.Objects.Charges;
import ShortCircuit.Controls.TowerControl;
import ShortCircuit.States.Game.GameState;
import com.jme3.asset.AssetManager;

/**
 * This class handles the upgrading of a tower/building of a tower
 * @author Connor Rice
 */
public class UpgradeTower implements Runnable {
    private GameState gs;
    private AssetManager assetManager;
    private int cost;
    private String type;
    private String matLoc;
    private float pitch;

    public UpgradeTower(GameState _gs, Object _type) {
        gs = _gs;
        type = (String) _type;
        this.assetManager = gs.getAssetManager();
        getUpgradeType();
    }
    
    /**
     * Determines the cost, name, and location of the texture of the next
     * available tower upgrade. ANGRYMONSTER ensures that you cannot go beyond
     * 4 modifications
     */
    private void getUpgradeType() {
        if (type.equals("unbuilt")) {
            cost = 100;
            type = "tower1";
            matLoc = "Materials/" +gs.getMatDir()+ "/Tower1.j3m";
            pitch = 0.6f;
        }
        else if (type.equals("tower1")) {
            cost = 50;
            type = "tower2";
            matLoc = "Materials/" +gs.getMatDir()+ "/Tower2.j3m";
            pitch = 0.8f;
        }
        else if (type.equals("tower2")) {
            cost = 100;
            type = "tower3";
            matLoc = "Materials/" +gs.getMatDir()+ "/Tower3.j3m";
            pitch = 0.9f;
        }
        else if (type.equals("tower3")) {
            cost = 500;
            type = "tower4";
            matLoc = "Materials/" +gs.getMatDir()+ "/Tower4.j3m";
            pitch = 1.0f;
        }
        else if (type.equals("tower4")) {
            type = "ANGRYMONSTER";
            pitch = 1f;
        }
    }
    
    /**
     * Checks to see if selected is actually a tower, also makes sure the tower
     * is upgradeable.
     * Mostly uses methods from GameState.
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
                    gs.playBuildSound(pitch);
                    if (type.equals("tower1")) {
                        tower.setBuilt();
                    }
                } 
            }
        }
    }

}
