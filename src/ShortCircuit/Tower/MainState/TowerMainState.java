package ShortCircuit.Tower.MainState;

import ShortCircuit.GUI.StartGUI;
import ShortCircuit.Tower.Cheats.CheatGUI;
import ShortCircuit.Tower.Cheats.CheatState;
import ShortCircuit.Tower.States.GUI.GameGUI;
import ShortCircuit.Tower.States.GUI.GameOverGUI;
import ShortCircuit.Tower.States.Game.AudioState;
import ShortCircuit.Tower.States.Game.GraphicsState;
import ShortCircuit.Tower.States.Game.EnemyState;
import ShortCircuit.Tower.States.Game.GameState;
import ShortCircuit.Tower.States.Game.LoadingState;
import ShortCircuit.Tower.States.Game.FriendlyState;
import ShortCircuit.Tower.States.Game.TutorialState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
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
    private EnemyState CreepState;
    private FriendlyState FriendlyState;
    private LoadingState LoadingState;
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
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private StartGUI StartGUI;
    private final boolean profile;
    private final String level;
    private TutorialState TutorialState;
    private FriendlyState HelperState;

    public TowerMainState(boolean _profile, String level) {
        this.profile = _profile;
        this.level = level;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.inputManager = this.app.getInputManager();
        this.assetManager = this.app.getAssetManager();
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
        LoadingState = new LoadingState(level);
        
        CheatState = new CheatState();
        CheatGUI = new CheatGUI();

        GameState = new GameState();
        GraphicsState = new GraphicsState();
        CreepState = new EnemyState();
        FriendlyState = new FriendlyState();
        HelperState = new FriendlyState();

        stateManager.attach(AudioState);
        stateManager.attach(GameState);
        stateManager.attach(FriendlyState);
        stateManager.attach(CreepState);
        stateManager.attach(GraphicsState);
        stateManager.attach(CheatState);
        stateManager.attach(LoadingState);
        stateManager.attach(GameGUI);
        stateManager.attach(CheatGUI);
        stateManager.attach(HelperState);
    }

    /**
     * Detaches all tower states.
     */
    public void detachStates() {
        stateManager.detach(GameGUI);
        stateManager.detach(GameState);
        stateManager.detach(FriendlyState);
        stateManager.detach(CreepState);
        stateManager.detach(GraphicsState);
        stateManager.detach(LoadingState);
        stateManager.detach(CheatState);
        stateManager.detach(CheatGUI);
        stateManager.detach(HelperState);
        stateManager.detach(TutorialState);
        stateManager.detach(AudioState);
    }



    /**
     * Changes global volume.
     * @param volume -New volume level. Not implemented yet.
     */
    public void setVolume(int volume) {
        app.getListener().setVolume(volume);
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
        if (!profile) {
            isPauseAllowed = false;
            stateManager.attach(GameOverGUI);
            detachStates();
        } else {
            /**
             * If we're in a profile session, we want to end the game.
             */
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
        CreepState.setEnabled(false);
        FriendlyState.setEnabled(false);
        HelperState.setEnabled(false);
    }

    /**
     * These are the states that are enabled on unpause.
     */
    public void enableTowerGameStates() {
        GameState.setEnabled(true);
        CreepState.setEnabled(true);
        LoadingState.setEnabled(true);
        GraphicsState.setEnabled(true);
        FriendlyState.setEnabled(true);
        HelperState.setEnabled(true);
    }

    public boolean isStartWindowShown() {
        return StartGUI.mainWindowShown();
    }

    public void goToMainMenu() {
        pause();
        StartGUI.toggle();
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
        asm.detach(FriendlyState);
        asm.detach(CreepState);
        asm.detach(GraphicsState);
        asm.detach(LoadingState);
        asm.detach(CheatState);
        asm.detach(CheatGUI);
        asm.detach(HelperState);
        asm.detach(AudioState);
    }
}
