package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.States.Game.GameState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * Factory for player controlled towers.
 * @author Connor Rice
 */
public class TowerFactory {
    private GameState gs;
    
    public TowerFactory(GameState _gs){
        gs = _gs;
    }

    public Spatial getTower(int index, Vector3f towervec, Vector3f 
            unbuiltTowerSize, String type) {
        Geometry tower_geom = new Geometry("Tower", gs.getUnivBox());
        tower_geom.setLocalScale(unbuiltTowerSize);
        tower_geom.setMaterial(gs.getAssetManager().loadMaterial(
                "Materials/"+gs.getMatDir()+"/TowerUnbuilt.j3m"));
        tower_geom.setLocalTranslation(towervec);
        Spatial tower = tower_geom;
        tower.setUserData("Location", towervec);
        tower.setUserData("Type", type);
        tower.setUserData("Index", index);
        tower.setUserData("BeamWidth", 6.0f);
        tower.setUserData("BeamType", "beam1");
        tower.addControl(new TowerControl(gs.getTowerState(), towervec));
        return tower;
    }
    
    
}
