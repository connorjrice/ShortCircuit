package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.States.Game.GameState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 *
 * @author Connor Rice
 */
public class TowerFactory {
    private GameState gs;
    private AssetManager assetManager;
    
    public TowerFactory(GameState _gs){
        gs = _gs;
        assetManager = gs.getAssetManager();
    }

    public Spatial getTower(int index, Vector3f towervec, Vector3f unbuiltTowerSize, String type) {
        Geometry tower_geom = new Geometry("Tower", gs.getUnivBox());
        tower_geom.setLocalScale(unbuiltTowerSize);
        tower_geom.setMaterial(assetManager.loadMaterial("Materials/"+gs.getMatDir()+"/UnbuiltTower.j3m"));
        tower_geom.setLocalTranslation(towervec);
        Spatial tower = tower_geom;
        tower.setUserData("Location", towervec);
        tower.setUserData("Type", type);
        tower.setUserData("Index", index);
        tower.setUserData("BeamWidth", 6.0f);
        tower.setUserData("BeamType", "beam1");
        tower.addControl(new TowerControl(gs.getBeamState(), gs.getTowerState(), towervec));
        return tower;
    }
    
    
}
