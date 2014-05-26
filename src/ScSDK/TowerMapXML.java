package ScSDK;

import ScSDK.IO.BuildState;
import ShortCircuit.States.Game.PathfindingState;
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

    public int width;
    public int height;
    private static final Trigger TRIGGER_ACTIVATE = new MouseButtonTrigger(
            MouseInput.BUTTON_LEFT);
    private final static String MAPPING_ACTIVATE = "Touch";
    private SimpleApplication app;
    private InputManager inputManager;
    private AppStateManager stateManager;
    private final String level;
    private BuildState BuildState;
    private PathfindingState PathState;

    public TowerMapXML(String level) {
        this.level = level;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.inputManager = this.app.getInputManager();
        this.stateManager = this.app.getStateManager();
        width = app.getContext().getSettings().getWidth();
        height = app.getContext().getSettings().getHeight();
        inputManager.setCursorVisible(true);
        inputManager.addMapping(MAPPING_ACTIVATE, TRIGGER_ACTIVATE);
        attachStates();
    }

    public void attachStates() {        
        BuildState = new BuildState(level);
        PathState = new PathfindingState(true);
        stateManager.attach(BuildState);
        stateManager.attach(PathState);
    }
    
    public Node getRootNode() {
        return this.app.getRootNode();
    }

    public void detachStates() {
        stateManager.detach(BuildState);
    }
    
    @Override
    public void stateDetached(AppStateManager asm) {
        asm.detach(BuildState);
    }



}
