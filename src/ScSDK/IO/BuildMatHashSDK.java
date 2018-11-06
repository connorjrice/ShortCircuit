package ScSDK.IO;

import com.jme3.asset.AssetManager;
import java.util.HashMap;

/**
 *
 * @author Connor Rice
 */
public class BuildMatHashSDK implements Runnable {
    private BuildState bs;
    private AssetManager assetManager;
    private String[] towerTypes;
    private HashMap matHash;
    
    public BuildMatHashSDK(BuildState bs, String[] towerTypes) {
        this.bs = bs;
        this.assetManager = bs.getAssetManager();
        this.towerTypes = towerTypes;
        this.matHash = new HashMap(20);
    }
    
    public void run() {
        for (int i = 0; i < towerTypes.length; i++) {
            matHash.put("Tower"+towerTypes[i], assetManager.
                    loadMaterial(bs.getMatLoc("Tower"+towerTypes[i])));
        }
        matHash.put("Bomb", assetManager.loadMaterial(bs.getMatLoc("Bomb")));
        matHash.put("CreepSpawner", assetManager.
                loadMaterial(bs.getMatLoc("CreepSpawner")));
        matHash.put("Base", assetManager.loadMaterial(bs.getMatLoc("Base")));
        matHash.put("Floor", assetManager.loadMaterial(bs.getMatLoc("Floor")));
        for (int i = 1; i < 5; i++) {
            matHash.put("Tower"+i+"Beam", assetManager.
                    loadMaterial(bs.getMatLoc("Tower"+i+"Beam")));
        }
    }
    
    public HashMap getMatHash() {
        return matHash;
    }
    
}
