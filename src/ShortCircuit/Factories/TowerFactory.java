package ShortCircuit.Factories;

import ShortCircuit.Controls.TowerControl;
import ShortCircuit.MapXML.TowerParams;
import ShortCircuit.States.Game.GraphicsState;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * Factory for player controlled towers.
 * @author Connor Rice
 */
public class TowerFactory {
    private GraphicsState gs;
    private AssetManager assetManager;
    
    public TowerFactory(GraphicsState _gs){
        gs = _gs;
        this.assetManager = gs.getAssetManager();
    }

    public Spatial getTower(TowerParams tp) {
        Geometry tower_geom = new Geometry("Tower", gs.getUnivBox());
        tower_geom.setMaterial(gs.getMaterial("TowerUnbuilt"));
        tower_geom.setLocalScale(gs.getTowerUnbuiltSize());
        tower_geom.setLocalTranslation(tp.getTowerVec());
        Spatial tower = tower_geom;
        TowerControl control = new TowerControl(gs.getFriendlyState(), tp.getTowerVec());
        tower.addControl(control);
        control.setBeamWidth(gs.getGeometryParams().getBeamWidth());
        tp.setControl(control);
        if (tp.getIsStarter()) {
            gs.towerUpgradeStarter(tp);
        }
        return tower;
    }
    
    
}
