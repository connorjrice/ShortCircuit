package ShortCircuit.Tower.MainState;

import ShortCircuit.GUI.StartGUI;
import ShortCircuit.Tower.Cheats.CheatGUI;
import ShortCircuit.Tower.Cheats.CheatState;
import ShortCircuit.Tower.States.GUI.GameGUI;
import ShortCircuit.Tower.States.GUI.GameOverGUI;
import ShortCircuit.Tower.States.Game.BeamState;
import ShortCircuit.Tower.States.Game.CreepState;
import ShortCircuit.Tower.States.Game.FilterState;
import ShortCircuit.Tower.States.Game.GameState;
import ShortCircuit.Tower.States.Game.HelperState;
import ShortCircuit.Tower.States.Game.LevelState;
import ShortCircuit.Tower.States.Game.TowerState;
import ShortCircuit.Tower.States.Game.TutorialState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;

/**
 * This is the main state for all Tower gameplay.
 * TODO: Document Tower Main State
 * @author Connor
 */
public class TowerMainState extends AbstractAppState {

    private GameState GameState;
    private BeamState BeamState;
    private CreepState CreepState;
    private TowerState TowerState;
    private LevelState LevelState;
    private CheatState CheatState;
    private GameGUI GameGUI;
    private GameOverGUI GameOverGUI;
    private CheatGUI CheatGUI;
    private boolean isPaused = false;
    private boolean isPauseAllowed = true;
    private boolean inGame = false;
    public int width;
    public int height;
    private static final Trigger TRIGGER_ACTIVATE = new MouseButtonTrigger(
            MouseInput.BUTTON_LEFT);
    private AudioNode theme;
    private final static String MAPPING_ACTIVATE = "Touch";
    private SimpleApplication app;
    private InputManager inputManager;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private StartGUI StartGUI;
    private final boolean profile;
    private final String level;
    private FilterState FilterState;
    private TutorialState TutorialState;
    private HelperState HelperState;

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
        underPinning();
        attachStates();
    }

    /**
     * Instantiates/attaches all states necessary for a tower game.
     * TODO: Figure out what all of these boolean vars do
     * TODO: Figure out which states need "this" in constructor
     */
    public void attachStates() {
        isPaused = false;
        isPauseAllowed = true;
        inGame = true;
        
        CheatState = new CheatState();
        FilterState = new FilterState();
        CheatGUI = new CheatGUI();
        GameState = new GameState();
        GameGUI = new GameGUI(this);
        LevelState = new LevelState(profile, level);
        BeamState = new BeamState();
        CreepState = new CreepState();
        TowerState = new TowerState();
        GameOverGUI = new GameOverGUI(this);
        HelperState = new HelperState();

        stateManager.attach(FilterState);
        stateManager.attach(GameState);
        stateManager.attach(TowerState);
        stateManager.attach(CreepState);
        stateManager.attach(BeamState);
        stateManager.attach(CheatState);
        stateManager.attach(LevelState);
        stateManager.attach(GameGUI);
        stateManager.attach(CheatGUI);
        stateManager.attach(HelperState);
    }

    /**
     * Detaches all tower states.
     * TODO: Figure out when this happens, document
     */
    public void detachStates() {
        stateManager.detach(GameGUI);
        stateManager.detach(GameState);
        stateManager.detach(TowerState);
        stateManager.detach(CreepState);
        stateManager.detach(BeamState);
        stateManager.detach(LevelState);
        stateManager.detach(CheatState);
        stateManager.detach(CheatGUI);
        stateManager.detach(FilterState);
        stateManager.detach(HelperState);
        theme.stop();
    }

    /**
     * Initializes the theme that plays throughout the game. Sets volume, sets
     * looping true, and begins playing.
     */
    public void underPinning() {
        theme = new AudioNode(assetManager, "Audio/underpinning.wav");
        theme.setVolume(1.0f);
        theme.setPositional(false);
        theme.setLooping(true);
        theme.play();
    }

    public void stopUnder() {
        theme.stop();
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
    public void backToStartGUI() {
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
     * TODO: Decide if this should be multipurpose (as it currently is)
     * or write more specific functions.
     * TODO: Sort out this boolean variable mess
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
            inGame = false;
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
        LevelState.setEnabled(false);
        BeamState.setEnabled(false);
        CreepState.setEnabled(false);
        TowerState.setEnabled(false);
        HelperState.setEnabled(false);
    }

    /**
     * These are the states that are enabled on unpause.
     */
    public void enableTowerGameStates() {
        GameState.setEnabled(true);
        CreepState.setEnabled(true);
        LevelState.setEnabled(true);
        BeamState.setEnabled(true);
        TowerState.setEnabled(true);
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
        if (inGame) {
            GameGUI.toggleFrills();
        }
    }
    
    @Override
    public void stateDetached(AppStateManager asm) {
        asm.detach(GameGUI);
        asm.detach(GameState);
        asm.detach(TowerState);
        asm.detach(CreepState);
        asm.detach(BeamState);
        asm.detach(LevelState);
        asm.detach(CheatState);
        asm.detach(CheatGUI);
        asm.detach(FilterState);
        asm.detach(HelperState);
    }
    
    @Override
    public void cleanup() {
        theme.stop();
    }
}
