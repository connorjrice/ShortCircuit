package ShortCircuit.States.GUI.Game;

import ShortCircuit.States.GUI.StartGUI;
import ShortCircuit.States.Game.AudioState;
import ShortCircuit.States.Game.FriendlyState;
import ShortCircuit.States.Game.GameState;
import ShortCircuit.States.Game.GraphicsState;
import ShortCircuit.TowerMainState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import tonegod.gui.core.Screen;

/**
 *
 * @author Connor
 */
public class PurchaseWindowState extends AbstractAppState {
    
    private TowerMainState tMS;
    private SimpleApplication app;
    private GameGUI gameGUI;
    private GameState GameState;
    private FriendlyState FriendlyState;
    private StartGUI StartGUI;
    private GraphicsState GraphicsState;
    private AudioState AudioState;
    private Screen screen;
    
    
    public PurchaseWindowState(TowerMainState _tMS) {
        this.tMS = _tMS;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.gameGUI = (GameGUI) stateManager.getState(GameGUI.class);
        this.GameState = (GameState) stateManager.getState(GameState.class);
        this.StartGUI = (StartGUI) stateManager.getState(StartGUI.class);
        this.FriendlyState = (FriendlyState) stateManager.getState(FriendlyState.class);
        this.GraphicsState = (GraphicsState) stateManager.getState(GraphicsState.class);
        this.AudioState = (AudioState) stateManager.getState(AudioState.class);
        this.screen = this.gameGUI.getScreen();
        layoutElements();
    }
    
    private void layoutElements() {
        
    }
    
    public void hide() {
        
    }
    
    public void show() {
        
    }
    
    @Override
    public void stateDetached(AppStateManager asm) {
        
    }
    
}
