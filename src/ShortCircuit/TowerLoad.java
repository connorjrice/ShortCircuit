package ShortCircuit;

import ShortCircuit.States.GUI.GameGUI;
import ShortCircuit.States.Game.EnemyState;
import ShortCircuit.States.Game.FriendlyState;
import ShortCircuit.States.Game.GameState;
import ShortCircuit.States.Game.PathfindingState;
import ShortCircuit.States.Game.GraphicsState;
import ShortCircuit.States.Game.LoadSavableState;
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
    private GraphicsState GraphicsState;
    private EnemyState EnemyState;
    private FriendlyState FriendlyState;
    private LoadSavableState LoadingState;
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
        GraphicsState = new GraphicsState();
        EnemyState = new EnemyState();
        FriendlyState = new FriendlyState();
        FriendlyState = new FriendlyState();
        PathfindingState = new PathfindingState();

        stateManager.attach(GameState);
        stateManager.attach(FriendlyState);
        stateManager.attach(EnemyState);
        stateManager.attach(GraphicsState);
        stateManager.attach(LoadingState);
        stateManager.attach(FriendlyState);
        stateManager.attach(PathfindingState);
        Node loadedNode = (Node)assetManager.loadModel("Models/Level1.lvl.xml.j3o");
        loadedNode.setName("loaded node");
        rootNode.attachChild(loadedNode);
    }
    
}
