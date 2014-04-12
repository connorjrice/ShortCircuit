package ShortCircuit.Factories;

import ShortCircuit.Controls.TowerControl;
import ShortCircuit.States.Game.GameState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Connor Rice
 */
public class TowerFactory {
    
    public TowerFactory(){}

    public Spatial getTower(int index, Vector3f towervec, Vector3f unbuiltTowerSize, String type,
            AssetManager assetManager, GameState gs) {
        Geometry tower_geom = new Geometry("Tower", new Box(1,1,1));
        tower_geom.setLocalScale(unbuiltTowerSize);
        tower_geom.setMaterial(assetManager.loadMaterial("Materials/"+gs.getMatDir()+"/UnbuiltTower.j3m"));
        tower_geom.setLocalTranslation(towervec);
        Spatial tower = tower_geom;
        tower.setUserData("Location", towervec);
        tower.setUserData("Type", type);
        tower.setUserData("Index", index);
        tower.addControl(new TowerControl(gs.getBeamState(), gs.getTowerState(), towervec, false));
        return tower;
    }
    
    
}
