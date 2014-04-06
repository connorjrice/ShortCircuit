package ShortCircuit;

import ShortCircuit.States.GUI.GameOverAppState;
import ShortCircuit.States.GUI.StartMenuState;
import ShortCircuit.States.GUI.PauseState;
import ShortCircuit.States.Game.GameState;
import ShortCircuit.States.Game.BeamState;
import ShortCircuit.States.Game.CreepState;
import ShortCircuit.States.Game.TowerState;
import ShortCircuit.States.GUI.GUIAppState;
import ShortCircuit.States.GUI.TGStart;
import ShortCircuit.States.Game.LevelState;
import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.renderer.ViewPort;
import java.util.logging.Level;
import java.util.logging.Logger;
import tonegod.gui.core.Screen;

/**
 * Tower Defense
 *
 * @author Connor Rice
 */

public class TowerDefenseMain extends SimpleApplication {

    /**
     * Glorious AppStates.
     */
    private GameState GameState;
    private GameOverAppState GameOverAppState;
    private StartMenuState StartMenu;
    private GUIAppState GuiState;
    private PauseState PauseState;
    private BeamState BeamState;
    private CreepState CreepState;
    private TowerState TowerState;
    private LevelState LevelState;
    

    /**
     * For interpreting single touches as mouse clicks.
     * Evenetually, implement multitouch
     */
    private static final Trigger TRIGGER_ACTIVATE = new MouseButtonTrigger(
            MouseInput.BUTTON_LEFT);
    private final static String MAPPING_ACTIVATE = "Touch";
    

    /**
     * Main Method
     * The Big Cheese
     * @param args 
     */
    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.OFF);
        TowerDefenseMain app = new TowerDefenseMain();
        app.start();
    }
    private TGStart TGStart;


    /**
     * Initialize camera, add input mapping, bring up start menu.
     * Also adjust statview/fps
     */
    @Override
    public void simpleInitApp() {
        flyCam.setDragToRotate(true);
        flyCam.setRotationSpeed(0.0f);
        flyCam.setZoomSpeed(0.0f);

        //setDisplayFps(false);
        setDisplayStatView(false);
        
        inputManager.setCursorVisible(true);
        inputManager.addMapping(MAPPING_ACTIVATE, TRIGGER_ACTIVATE);

        //showStartMenu();
        showTGMenu();
    }
    
    /**
     * Instantiates and attaches Start Menu.
     */
    public void showStartMenu() {
        StartMenu = new StartMenuState(this);
        stateManager.attach(StartMenu);
    }
    
    public void showTGMenu() {
        TGStart = new TGStart();
        stateManager.attach(TGStart);
    }
    
    /**
     * Instantiates all Game States, detaches Start Menu.
     * Then, starts the game by attaching all of the states.
     * @param debug - different method calls need to happen for debug games.
     * @param levelName - Name of the level to be loaded by LevelState
     * The levelName is given by the StartMenu state.
     * The levelName corresponds to an XML file located in:
     * assets/XML; the file extension is .lvl.xml
     */
    public void startGame(boolean debug, String levelName) {
        stateManager.detach(StartMenu);
        GameState = new GameState();
        LevelState = new LevelState(debug, levelName);
        GuiState = new GUIAppState(this);
        PauseState = new PauseState(GuiState);
        GameOverAppState = new GameOverAppState(this);
        BeamState = new BeamState();
        CreepState = new CreepState();
        TowerState = new TowerState();
        PauseState.setEnabled(false);
        GameOverAppState.setEnabled(false);
        inputManager.setCursorVisible(true);
        attachGameStates();
    }
    
    
    /**
     * Attaches all of the states necessary for playing a level.
     * Called by startGame().
     */
    private void attachGameStates() {
        stateManager.attach(GuiState);
        stateManager.attach(GameState);
        stateManager.attach(TowerState);
        stateManager.attach(CreepState);
        stateManager.attach(BeamState);
        stateManager.attach(LevelState);
        stateManager.attach(PauseState);
        stateManager.attach(GameOverAppState); 
    }
    
    /**
     * Detaches all of the states necessary for playing a level.
     * Called when starting/continuing from the Start menu.
     */
    public void detachGameStates() {
        stateManager.detach(GuiState);
        stateManager.detach(GameState);
        stateManager.detach(TowerState);
        stateManager.detach(CreepState);
        stateManager.detach(BeamState);
        stateManager.detach(LevelState);
        stateManager.detach(PauseState);
        stateManager.detach(GameOverAppState);
    }
    
    /**
     * Returns the GUI Viewport
     * @return guiViewPort
     * Called by GUI and Start states.
     */
    public ViewPort getGUIViewport() {
        return guiViewPort;
    }
    
    /**
     * Pauses the game.
     * Called by GUI state.
     */
    public void pause() {
        disableStates();
        PauseState.setEnabled(true);
        PauseState.enable();
    }
    
    /**
     * Unpauses the game.
     * Called by the  Pause state.
     */
    public void unpause() {
        enableStates();
        PauseState.disable();
        PauseState.setEnabled(false);
    }
    
    /**
     * Ends game and detaches the states so they cannot be retrieved.
     * This is handled by the GameOverAppState through a call from LevelState.
     * TODO: Implement high scores state/list
     */
    public void gameover() {
       disableStates();
    }
    
    /**
     * These are the states that are disabled on pause.
     */
    public void disableStates() {
        GameState.setEnabled(false);
        LevelState.setEnabled(false);
        BeamState.setEnabled(false);
        CreepState.setEnabled(false);
        TowerState.setEnabled(false);
        GuiState.setEnabled(false);
    }
    /** 
     * These are the states that are enabled on unpause.
     */
    public void enableStates() {
        GameState.setEnabled(true);
        CreepState.setEnabled(true);
        LevelState.setEnabled(true);
        BeamState.setEnabled(true);
        TowerState.setEnabled(true);
        GuiState.setEnabled(true);
    }
    
    /**
     * This is called from the pause menu to get back to the main menu.  False
     * Alternatively, it is called from the GameOver state, with boolean True
     */
    public void goToMainMenu(boolean isLost) {
        if (!isLost) {
            disableStates();
        }
        stateManager.detach(GuiState);
        stateManager.attach(StartMenu);
        if (!isLost) {
            StartMenu.attachContinueButton();
            PauseState.disable();
            PauseState.setEnabled(false);
        }
        else {
            GameOverAppState.disable();
            GameOverAppState.setEnabled(false);
        }
        
    }
    
    
    public void continueFromPaused() {
        enableStates();
        stateManager.attach(GuiState);
        stateManager.detach(StartMenu);
    }
    
    @Override
    public void destroy() {
        super.destroy();
        //GameState.ex.shutdown();
    }
   

}
