package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.Objects.Charges;
import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.States.Game.GameState;

/**
 * This class handles the upgrading of a tower/building of a tower
 * TODO: Document tower upgrade factory
 * @author Connor Rice
 */
public class TowerUpgradeFactory {

    private int cost;
    private String matLoc;
    private float pitch;
    private GameState gs;
    private boolean valid;

    public TowerUpgradeFactory(GameState _gs) {
        gs = _gs;
    }

    public void run() {
        String type = gs.getTowerList().get(gs.getSelected()).getUserData("Type");
        if (type.equals("unbuilt")) {
            cost = 100;
            type = "1";
            matLoc = "Materials/" + gs.getMatDir() + "/Tower1.j3m";
            pitch = 0.6f;
            valid = true;
        } else if (type.equals("Tower1")) {
            cost = 50;
            type = "2";
            matLoc = "Materials/" + gs.getMatDir() + "/Tower2.j3m";
            pitch = 0.8f;
            valid = true;

        } else if (type.equals("Tower2")) {
            cost = 100;
            type = "3";
            matLoc = "Materials/" + gs.getMatDir() + "/Tower3.j3m";
            pitch = 0.9f;
            valid = true;
        } else if (type.equals("Tower3")) {
            cost = 500;
            type = "4";
            matLoc = "Materials/" + gs.getMatDir() + "/Tower4.j3m";
            pitch = 1.0f;
            valid = true;
        }
        if (gs.getSelected() != -1 && valid) {
            TowerControl tower = gs.getTowerList().get(gs.getSelected()).getControl(TowerControl.class);
            if (gs.getPlrBudget() >= cost) {
                tower.charges.add(new Charges("Tower" + type));
                tower.setBuilt();
                tower.setTowerType("Tower" + type);
                tower.setBeamType("beam" + type);
                if (type.equals("4")) {
                    gs.incFours();
                }
                tower.getSpatial().setMaterial(gs.getAssetManager().loadMaterial(matLoc));
                tower.setSize(gs.getBuiltTowerSize());
                gs.decPlrBudget(cost);
                gs.playBuildSound(pitch);
                valid = false;


            }
        }
    }
}
