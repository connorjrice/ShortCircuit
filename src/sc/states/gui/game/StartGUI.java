package sc.states.gui.game;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 *
 * @author Connor
 */
public class StartGUI extends AbstractAppState {
    
    private SimpleApplication app;
    private Node guiNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    
    private boolean mainWindowShown;
    
    @Override
    public void initialize(AppStateManager asm, Application app) {
        super.initialize(asm, app);
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        displayStartMenu();
    }
    
    private void displayStartMenu() {
        mainWindowShown = true;
    }
    
    public boolean isMainWindowShown() {
        return mainWindowShown;
    }
    
    public void hideLoadingScreen() {
        
    }
    
}
