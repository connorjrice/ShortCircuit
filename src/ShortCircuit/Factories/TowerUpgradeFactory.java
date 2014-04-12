package ShortCircuit.Factories;

import ShortCircuit.Objects.Charges;
import ShortCircuit.Controls.TowerControl;
import ShortCircuit.States.Game.GameState;
import com.jme3.asset.AssetManager;

/**
 * This class handles the upgrading of a tower/building of a tower
 * @author Connor Rice
 */
public class TowerUpgradeFactory {
    private int cost;
    private String matLoc;
    private float pitch;
    private String beamType;
    private AssetManager assetManager;
    private GameState gs;


    public TowerUpgradeFactory(AssetManager _assetManager, GameState _gs) {
        assetManager = _assetManager;
        gs = _gs;
    }      

    public void run(String type) {
        if (type.equals("unbuilt")) {
            cost = 100;
            type = "1";
            matLoc = "Materials/" +gs.getMatDir()+ "/Tower1.j3m";
            pitch = 0.6f;
        }
        else if (type.equals("tower1")) {
            cost = 50;
            type = "2";
            matLoc = "Materials/" +gs.getMatDir()+ "/Tower2.j3m";
            pitch = 0.8f;
        }
        else if (type.equals("tower2")) {
            cost = 100;
            type = "3";
            matLoc = "Materials/" +gs.getMatDir()+ "/Tower3.j3m";
            pitch = 0.9f;
        }
        else if (type.equals("tower3")) {
            cost = 500;
            type = "4";
            matLoc = "Materials/" +gs.getMatDir()+ "/Tower4.j3m";
            pitch = 1.0f;
        }
        else if (type.equals("tower4")) {
            type = "ANGRYMONSTER";
            pitch = 1f;
        }
    
        if (gs.getSelected() != -1) {
            if (!type.equals("ANGRYMONSTER")) {
                TowerControl tower = gs.getTowerList().get(gs.getSelected()).getControl(TowerControl.class);
                if (gs.getPlrBudget() >= cost) {
                    tower.charges.add(new Charges("tower"+type));
                    tower.setTowerType("tower"+type);
                    tower.setBeamType("beam"+type);
                    if (type.equals("4")) {
                        gs.incFours();
                    }
                    tower.getSpatial().setMaterial(assetManager.loadMaterial(matLoc));
                    tower.setSize(gs.getTowerState().getBuiltTowerSize());
                    gs.decPlrBudget(cost);
                    gs.playBuildSound(pitch);
                    if (type.equals("1")) {
                        tower.setBuilt();
                    }

                } 
            }
        }
    }

}
