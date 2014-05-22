package ScSDK;

import ScSDK.IO.BuildState;
import ShortCircuit.States.GUI.StartGUI;
import ShortCircuit.States.Game.GraphicsState;
import ShortCircuit.States.Game.EnemyState;
import ShortCircuit.States.Game.GameState;
import ScSDK.IO.LoadingState;
import ShortCircuit.States.Game.FriendlyState;
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
import com.jme3.scene.Node;

/**
 * This is the main state for all Tower gameplay.
 * @author Connor
 */
public class TowerMapXML extends AbstractAppState {

    private GameState GameState;
    private GraphicsState GraphicsState;
    private EnemyState EnemyState;
    private FriendlyState FriendlyState;
    private LoadingState LoadingState;
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
    private final boolean profile;
    private final String level;
    private TutorialState TutorialState;
    private FriendlyState HelperState;
    private PathfindingState PathfindingState;
    private BuildState BuildState;

    public TowerMapXML(boolean _profile, String level) {
        this.profile = _profile;
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
        
        LoadingState = new LoadingState(level);
        BuildState = new BuildState();
        GameState = new GameState();
        GraphicsState = new GraphicsState(true);
        EnemyState = new EnemyState();
        FriendlyState = new FriendlyState();
        HelperState = new FriendlyState();
        PathfindingState = new PathfindingState();

        stateManager.attach(BuildState);
        stateManager.attach(GameState);
        stateManager.attach(FriendlyState);
        stateManager.attach(EnemyState);
        stateManager.attach(GraphicsState);
        stateManager.attach(LoadingState);
        stateManager.attach(HelperState);
        stateManager.attach(PathfindingState);
        disableTowerGameStates();
    }
    
    public Node getRootNode() {
        return GameState.getRootNode();
    }

    /**
     * Detaches all tower states.
     */
    public void detachStates() {
        stateManager.detach(GameState);
        stateManager.detach(FriendlyState);
        stateManager.detach(EnemyState);
        stateManager.detach(GraphicsState);
        stateManager.detach(LoadingState);
        stateManager.detach(HelperState);
        stateManager.detach(TutorialState);
        stateManager.detach(PathfindingState);
    }



    /**
     * Changes global volume.
     * @param volume -New volume level. Not implemented yet.
     */
    public void setVolume(int volume) {
        app.getListener().setVolume(volume);
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

     /*
     * These are the states that are disabled on pause.
     */
    public void disableTowerGameStates() {
        GameState.setEnabled(false);
        LoadingState.setEnabled(false);
        GraphicsState.setEnabled(false);
        EnemyState.setEnabled(false);
        FriendlyState.setEnabled(false);
        HelperState.setEnabled(false);
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

    @Override
    public void stateDetached(AppStateManager asm) {
        asm.detach(GameState);
        asm.detach(FriendlyState);
        asm.detach(EnemyState);
        asm.detach(GraphicsState);
        asm.detach(LoadingState);
        asm.detach(HelperState);
        asm.detach(BuildState);
    }

}
