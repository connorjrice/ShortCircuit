/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit;

import ShortCircuit.States.Game.EnemyState;
import ShortCircuit.States.Game.FriendlyState;
import ShortCircuit.States.Game.GameState;
import ShortCircuit.States.Game.GraphicsState;
import ShortCircuit.States.Game.LoadingState;
import ShortCircuit.States.Game.PathfindingState;
import ShortCircuit.States.Game.ProfileState;
import ShortCircuit.States.Game.TutorialState;
import ShortCircuit.States.Savable.GraphicsSavableState;
import ShortCircuit.States.Savable.LoadSavableState;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 *
 * @author Development
 */
public class TowerLoad extends SimpleApplication {
    
    private GameState GameState;
    private GraphicsSavableState GraphicsState;
    private EnemyState EnemyState;
    private FriendlyState FriendlyState;
    private LoadSavableState LoadingState;
    private TutorialState TutorialState;
    private FriendlyState HelperState;
    private PathfindingState PathfindingState;
    
    public static void main(String[] args) {
        TowerLoad app = new TowerLoad();
        AppSettings sets = new AppSettings(true);
        sets.setResolution(1920,1080);
        sets.setFrequency(60);
        sets.setFullscreen(false);
        sets.setVSync(true);
        sets.setFrameRate(60);
        app.setSettings(sets);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        String userHome = System.getProperty("user.home");
        assetManager.registerLocator(userHome, FileLocator.class);
        LoadingState = new LoadSavableState("Level1.lvl.xml");
        GameState = new GameState();
        GraphicsState = new GraphicsSavableState();
        EnemyState = new EnemyState();
        FriendlyState = new FriendlyState();
        HelperState = new FriendlyState();
        PathfindingState = new PathfindingState();


        stateManager.attach(GameState);
        stateManager.attach(FriendlyState);
        stateManager.attach(EnemyState);
        stateManager.attach(GraphicsState);
        stateManager.attach(LoadingState);
        stateManager.attach(HelperState);
        stateManager.attach(PathfindingState);
        Node loadedNode = (Node)assetManager.loadModel("Models/MyModel.j3o");
        loadedNode.setName("loaded node");
        rootNode.attachChild(loadedNode);
    }
    
}
