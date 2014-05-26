package ShortCircuit.Unimplemented;

import ScSDK.IO.BuildState;
import ShortCircuit.States.Game.GraphicsState;
import com.jme3.asset.AssetManager;
import java.util.HashMap;

/**
 *
 * @author Development
 */
public class BuildMatHash implements Runnable {
    private GraphicsState gs;
    private AssetManager assetManager;
    private String[] towerTypes;
    private Object[] creepTypes;
    private HashMap matHash;
    
    public BuildMatHash(GraphicsState gs, String[] towerTypes, Object[] creepTypes) {
        this.gs = gs;
        this.assetManager = gs.getAssetManager();
        this.towerTypes = towerTypes;
        this.creepTypes = creepTypes;
        this.matHash = new HashMap(20);
    }
    
    public void run() {
        for (int i = 0; i < towerTypes.length; i++) {
            matHash.put("Tower"+towerTypes[i], assetManager.loadMaterial(gs.getMatLoc("Tower"+towerTypes[i])));
        }
        matHash.put("Bomb", assetManager.loadMaterial(gs.getMatLoc("Bomb")));
        matHash.put("CreepSpawner", assetManager.loadMaterial(gs.getMatLoc("CreepSpawner")));
        matHash.put("Base", assetManager.loadMaterial(gs.getMatLoc("Base")));
        matHash.put("Floor", assetManager.loadMaterial(gs.getMatLoc("Floor")));
        for (int i = 1; i < 5; i++) {
            matHash.put("Tower"+i+"Beam", assetManager.loadMaterial(gs.getMatLoc("Tower"+i+"Beam")));
        }
        for (int i = 0; i < creepTypes.length; i++) {
            matHash.put(creepTypes[i]+"Creep", assetManager.loadMaterial(gs.getMatLoc(creepTypes[i]+"Creep")));
        }
    }
    
    public HashMap getMatHash() {
        return matHash;
    }
    
}
