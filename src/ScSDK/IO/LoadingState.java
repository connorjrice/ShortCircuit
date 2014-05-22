package ScSDK.IO;

import ScSDK.IO.BuildState;
import ShortCircuit.States.GUI.StartGUI;
import ScSDK.MapXML.MapGenerator;
import ShortCircuit.States.Game.GameState;
import ShortCircuit.States.Game.GraphicsState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 * LoadingState calls the appropriate methods in its sibling states to create a 
 * level. A level is defined at this moment in time as a floor, at least one 
 * base vector, n number of towers, and n number of creep spawners.
 * @author Connor Rice
 */
public class LoadingState extends AbstractAppState {
    private SimpleApplication app;
    private GameState GameState;
    private MapGenerator mg;
    private String levelName;
    private AppStateManager stateManager;
    private GraphicsState GraphicsState;
    private BuildState BuildState;
    
    public LoadingState() {}
    
    public LoadingState(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.stateManager = stateManager;
        this.GraphicsState = this.stateManager.getState(GraphicsState.class);
        this.GameState = this.stateManager.getState(GameState.class);
        this.BuildState = this.stateManager.getState(BuildState.class);
        newGame();
    }
    

    public void newGame() {
        initMG(levelName, app);
        BuildState.createWorld(mg.getGraphicsParams());
        GraphicsState.setGPBuild(mg.getGraphicsParams());
        GameState.setGPBuild(mg.getGameplayParams());
    }

    private void initMG(String levelname, SimpleApplication app) {
        mg = new MapGenerator(levelname, app);
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

}