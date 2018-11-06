package ShortCircuit.States.Game;

import ShortCircuit.TowerMainState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;

/**
 *
 * @author Connor
 */
public class SaveFileState extends AbstractAppState {
    
    private TowerMainState tMS;
    private SimpleApplication app;
    private AssetManager assetManager;
    private GameState GameState;
    
    public SaveFileState(TowerMainState _tMS) {
        this.tMS = _tMS;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.GameState = stateManager.getState(GameState.class);
    }
    
    public void loadFile() {
        
    }
    
    public void saveFile() {
        
    }
    
    public int getUnlockedLevels() {
        return -1;
    }
    
    public int[] getHighScores() {
        return null;
    }
    
}
