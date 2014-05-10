package ShortCircuit.Factories;

import ShortCircuit.Controls.TowerControl;
import ShortCircuit.MapXML.Objects.TowerParams;
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

    public TowerParams getTower(TowerParams tp) {
        Geometry tower_geom = new Geometry("Tower", gs.getUnivBox());
        String matloc = gs.getTowerMatLoc("TowerUnbuilt");
        tower_geom.setMaterial(assetManager.loadMaterial(matloc));
        tower_geom.setLocalScale(gs.getTowerUnbuiltSize());
        tower_geom.setLocalTranslation(tp.getTowerVec());
        Spatial tower = tower_geom;
        tp.setSpatial(tower);
        tp.setType("TowerUnbuilt");
        tp.setIndex();
        TowerControl control = new TowerControl(gs.getFriendlyState(), tp.getTowerVec());
        tower.addControl(control);
        control.setBeamWidth(gs.getGeometryParams().getBeamWidth());
        tp.setControl(control);
        if (tp.getIsStarter()) {
            gs.towerUpgradeStarter(tp);
        }
        return tp;
    }
    
    
}
