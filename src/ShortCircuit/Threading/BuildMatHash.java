/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.Threading;

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
        for (int i = 0; i < creepTypes.length; i++) {
            matHash.put(creepTypes[i]+"Creep", assetManager.loadMaterial(gs.getMatLoc(creepTypes[i]+"Creep")));
        }
    }
    
    public HashMap getMatHash() {
        return matHash;
    }
    
}
