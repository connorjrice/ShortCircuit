package ShortCircuit.Tower.States.Game;

import ShortCircuit.GUI.StartGUI;
import ShortCircuit.Tower.MapXML.MapGenerator;
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
    private EnemyState EnemyState;
    private MapGenerator mg;
    private final String levelName;
    private AppStateManager stateManager;
    private StartGUI StartGUI;
    private GraphicsState GraphicsState;
    
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
        this.EnemyState = this.stateManager.getState(EnemyState.class);
        this.StartGUI = this.stateManager.getState(StartGUI.class);
        newGame();
    }
    

    public void newGame() {
        initMG(levelName);
        GraphicsState.setGraphicsParams(mg.getGraphicsParams());
        EnemyState.setEnemyParams(mg.getEnemyParams());
        GameState.setGameplayParams(mg.getGameplayParams());

    }

    private void initMG(String levelname) {
        mg = new MapGenerator(levelname, app);
    }
    
    private void updateStartGUI() {
        StartGUI.hideloading();
        StartGUI.updateAtlas(GameState.getAtlas());
    }

   
    @Override
    public void cleanup() {
        super.cleanup();
    }

}