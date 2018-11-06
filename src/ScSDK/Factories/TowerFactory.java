package ScSDK.Factories;

import ScSDK.IO.BuildState;
import ScSDK.MapXML.TowerParams;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * Factory for player controlled towers.
 *
 * @author Connor Rice
 */
public class TowerFactory {

    private BuildState bs;

    public TowerFactory(BuildState bs) {
        this.bs = bs;
    }

    public Spatial getTower(TowerParams tp) {
        Geometry tower_geom = new Geometry("Tower", bs.getUnivBox());
        tower_geom.setLocalTranslation(tp.getTowerVec());
        tower_geom.setUserData("Index", tp.getIndex());
        if (tp.getIsStarter()) {
            tower_geom.setUserData("Type", "Tower1");
            tower_geom.setLocalScale(bs.getTowerBuiltSize());
        } else {
            tower_geom.setUserData("Type", "TowerUnbuilt");
            tower_geom.setLocalScale(bs.getTowerUnbuiltSize());
        }
        tower_geom.setMaterial(bs.getMaterial((String) 
                tower_geom.getUserData("Type")));
        tower_geom.setUserData("BeamWidth", tp.getBeamWidth());
        return tower_geom;
    }
}
