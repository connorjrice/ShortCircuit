package ScSDK.Factories;

import ScSDK.IO.BuildState;
import ShortCircuit.MapXML.TowerParams;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * Factory for player controlled towers.
 * @author Connor Rice
 */
public class TowerFactory {
    private BuildState bs;
    private AssetManager assetManager;
    
    public TowerFactory(BuildState bs){
        this.bs = bs;
        this.assetManager = bs.getAssetManager();
    }

    public TowerParams getTower(TowerParams tp) {
        Geometry tower_geom = new Geometry("Tower", bs.getUnivBox());
        tower_geom.setMaterial(bs.getMaterial("TowerUnbuilt"));
        tower_geom.setLocalScale(bs.getTowerUnbuiltSize());
        tower_geom.setLocalTranslation(tp.getTowerVec());
        Spatial tower = tower_geom;
        tp.setSpatial(tower);
        return tp;
    }
    
    
}
