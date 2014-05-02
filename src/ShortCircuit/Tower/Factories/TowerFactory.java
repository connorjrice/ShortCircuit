package ShortCircuit.Tower.Factories;

import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.MapXML.Objects.TowerParams;
import ShortCircuit.Tower.States.Game.GraphicsState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * Factory for player controlled towers.
 * @author Connor Rice
 */
public class TowerFactory {
    private GraphicsState gs;
    
    public TowerFactory(GraphicsState _gs){
        gs = _gs;
    }

    public Spatial getTower(TowerParams _tp) {
        Geometry tower_geom = new Geometry("Tower", gs.getUnivBox());
        tower_geom.setLocalScale(gs.getTowerUnbuiltSize());
        tower_geom.setMaterial(gs.getAssetManager().loadMaterial(
                "Materials/"+gs.getMatDir()+"/TowerUnbuilt.j3m"));
        tower_geom.setLocalTranslation(_tp.getTowerVec());
        Spatial tower = tower_geom;
        tower.setUserData("Type", _tp.getType());
        tower.setUserData("Index", _tp.getIndex());
        tower.setUserData("BeamWidth", 6.0f);
        tower.setUserData("BeamType", "beam1");
        tower.addControl(new TowerControl(gs.getFriendlyState(), _tp.getTowerVec()));
        return tower;
    }
    
    
}
