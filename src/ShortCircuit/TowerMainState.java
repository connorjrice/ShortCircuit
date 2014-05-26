package ShortCircuit;

import ShortCircuit.States.GUI.StartGUI;
import ShortCircuit.States.GUI.CheatGUI;
import ShortCircuit.States.Game.CheatState;
import ShortCircuit.States.GUI.GameGUI;
import ShortCircuit.States.GUI.GameOverGUI;
import ShortCircuit.States.Game.AudioState;
import ShortCircuit.States.Game.GraphicsState;
import ShortCircuit.States.Game.EnemyState;
import ShortCircuit.States.Game.GameState;
import ShortCircuit.States.Game.FriendlyState;
import ShortCircuit.States.Game.LoadSavableState;
import ShortCircuit.States.Game.PathfindingState;
import ShortCircuit.States.Game.TutorialState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;

/**
 * This is the main state for all Tower gameplay.
 * @author Connor
 */
public class TowerMainState extends AbstractAppState {

    private GameState GameState;
    private GraphicsState GraphicsState;
    private EnemyState EnemyState;
    private FriendlyState FriendlyState;
    private LoadSavableState LoadingState;
    private CheatState CheatState;
    private AudioState AudioState;
    private GameGUI GameGUI;
    private GameOverGUI GameOverGUI;
    private CheatGUI CheatGUI;
    private boolean isPaused = false;
    private boolean isPauseAllowed = true;
    public int width;
    public int height;
    private static final Trigger TRIGGER_ACTIVATE = new MouseButtonTrigger(
            MouseInput.BUTTON_LEFT);
    private final static String MAPPING_ACTIVATE = "Touch";
    private SimpleApplication app;
    private InputManager inputManager;
    private AppStateManager stateManager;
    private StartGUI StartGUI;
    private String level;
    private TutorialState TutorialState;
    private PathfindingState PathfindingState;
    private boolean isProfile = false;
    
    public TowerMainState() {
        level = "Level1.lvl.xml";
    }

    public TowerMainState(String level) {
        this.level = level;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.inputManager = this.app.getInputManager();
        this.stateManager = this.app.getStateManager();
        this.StartGUI = stateManager.getState(StartGUI.class);
        width = app.getContext().getSettings().getWidth();
        height = app.getContext().getSettings().getHeight();
        inputManager.setCursorVisible(true);
        inputManager.addMapping(MAPPING_ACTIVATE, TRIGGER_ACTIVATE);
        attachStates();
    }

    /**
     * Instantiates/attaches all states necessary for a tower game.
     */
    public void attachStates() {
        isPaused = false;
        isPauseAllowed = true; 
        
        GameGUI = new GameGUI(this);
        GameOverGUI = new GameOverGUI(this);
        
        AudioState = new AudioState();
        LoadingState = new LoadSavableState(level);
        
        CheatState = new CheatState();
        CheatGUI = new CheatGUI();

        GameState = new GameState();
        GraphicsState = new GraphicsState();
        EnemyState = new EnemyState();
        FriendlyState = new FriendlyState();
        FriendlyState = new FriendlyState();
        PathfindingState = new PathfindingState();
        


        stateManager.attach(AudioState);
        stateManager.attach(GameState);
        stateManager.attach(FriendlyState);
        stateManager.attach(EnemyState);
        stateManager.attach(GraphicsState);
        stateManager.attach(CheatState);
        stateManager.attach(LoadingState);
        stateManager.attach(GameGUI);
        stateManager.attach(CheatGUI);
        stateManager.attach(FriendlyState);
        stateManager.attach(PathfindingState);
    }

    /**
     * Detaches all tower states.
     */
    public void detachStates() {
        stateManager.detach(GameGUI);
        stateManager.detach(GameState);
        stateManager.detach(FriendlyState);
        stateManager.detach(EnemyState);
        stateManager.detach(GraphicsState);
        stateManager.detach(LoadingState);
        stateManager.detach(CheatState);
        stateManager.detach(CheatGUI);
        stateManager.detach(FriendlyState);
        stateManager.detach(TutorialState);
        stateManager.detach(AudioState);
        stateManager.detach(PathfindingState);
    }
    
    /**
     * Returns to the start menu.
     * Currently used by GameOver state, the "Too Bad" button.
     */
    public void returnToStartAfterGameOver() {
        detachStates();
        stateManager.detach(GameOverGUI);
        stateManager.attach(StartGUI);
        StartGUI.toggle();
    }

    /**
     * Toggles the window for the cheats.
     */
    public void toggleCheatsWindow() {
        CheatGUI.toggleCheatWindow();
    }

    /**
     * Pauses the game.
     */
    public void pause() {
        if (isPauseAllowed) {
            if (isPaused) {
                enableTowerGameStates();
                isPaused = false;
            } else {
                disableTowerGameStates();
                isPaused = true;
            }
        }
    }

    /**
     * Ends game and detaches the states so they cannot be retrieved.
     */
    public void gameover() {
        if (!LoadingState.getProfile()) {
            isPauseAllowed = false;
            stateManager.attach(GameOverGUI);
            detachStates();
        } else {
            super.cleanup();
            cleanup();
            app.stop();
            System.exit(0);
        }
    }

    /**
     * These are the states that are disabled on pause.
     */
    public void disableTowerGameStates() {
        GameState.setEnabled(false);
        LoadingState.setEnabled(false);
        GraphicsState.setEnabled(false);
        EnemyState.setEnabled(false);
        FriendlyState.setEnabled(false);
        FriendlyState.setEnabled(false);
    }

    /**
     * These are the states that are enabled on unpause.
     */
    public void enableTowerGameStates() {
        GameState.setEnabled(true);
        EnemyState.setEnabled(true);
        LoadingState.setEnabled(true);
        GraphicsState.setEnabled(true);
        FriendlyState.setEnabled(true);
        FriendlyState.setEnabled(true);
    }

    public boolean isStartWindowShown() {
        return StartGUI.mainWindowShown();
    }

    public void goToMainMenu() {
        pause();
        GameGUI.toggle(StartGUI.toggle());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void toggleFrills() {
        GameGUI.toggleFrills();
    }
    
    public void stopTheme() {
        AudioState.stopTheme();
    }
    
    @Override
    public void stateDetached(AppStateManager asm) {
        asm.detach(GameGUI);
        asm.detach(GameState);
        asm.detach(PathfindingState);
        asm.detach(FriendlyState);
        asm.detach(EnemyState);
        asm.detach(GraphicsState);
        asm.detach(LoadingState);
        asm.detach(CheatState);
        asm.detach(CheatGUI);
        asm.detach(FriendlyState);
        asm.detach(AudioState);
        inputManager.clearMappings();
    }

}
