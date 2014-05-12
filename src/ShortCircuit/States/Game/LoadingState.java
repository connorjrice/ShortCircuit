package ShortCircuit.States.Game;

import ShortCircuit.States.GUI.StartGUI;
import ShortCircuit.MapXML.MapGenerator;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            newGame();
        } catch (Exception ex) {
            Logger.getLogger(LoadingState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void newGame() throws Exception {
        initMG(levelName, app);
        GraphicsState.setGraphicsParams(mg.getGraphicsParams());
        EnemyState.setEnemyParams(mg.getEnemyParams());
        GameState.setGameplayParams(mg.getGameplayParams());
    }

    private void initMG(String levelname, SimpleApplication app) {
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