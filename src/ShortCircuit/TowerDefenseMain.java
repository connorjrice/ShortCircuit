package ShortCircuit;

import ShortCircuit.States.Game.GameState;
import ShortCircuit.States.Game.BeamState;
import ShortCircuit.States.Game.CreepState;
import ShortCircuit.States.Game.TowerState;
import ShortCircuit.States.GUI.GameGUI;
import ShortCircuit.States.GUI.GameOverGUI;
import ShortCircuit.States.GUI.StartGUI;
import ShortCircuit.States.Game.BombState;
import ShortCircuit.States.Game.LevelState;
import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.BloomFilter.GlowMode;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ShortCircuit: A tower defense game
 * @author Connor Rice
 */

public class TowerDefenseMain extends SimpleApplication {

    /**
     * Glorious AppStates.
     */
    private GameState GameState;
    private BeamState BeamState;
    private CreepState CreepState;
    private TowerState TowerState;
    private LevelState LevelState;
    private BombState BombState;
    private StartGUI StartGUI;
    private GameGUI GameGUI;
    private GameOverGUI GameOverGUI;
    private boolean isPaused = false;
    private boolean isPauseAllowed = true;
    private boolean inGame = false;
    
    public int width;
    public int height;

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






    /**
     * Initialize camera, add input mapping, bring up start menu.
     * Also adjust statview/fps
     */
    @Override
    public void simpleInitApp() {
        flyCam.setDragToRotate(true);
        flyCam.setRotationSpeed(0.0f);
        flyCam.setZoomSpeed(0.0f);

        width = getContext().getSettings().getWidth();
        height = getContext().getSettings().getHeight();
        setDisplayFps(false);
        setDisplayStatView(false);
        
        inputManager.setCursorVisible(true);
        inputManager.addMapping(MAPPING_ACTIVATE, TRIGGER_ACTIVATE);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        BloomFilter bloom = new BloomFilter(GlowMode.Objects);
        bloom.setDownSamplingFactor(2.0f);
        fpp.addFilter(bloom);
        viewPort.addProcessor(fpp);
        
        showTGStart();

    }
    
    
    public void showTGStart() {
        StartGUI = new StartGUI(this);
        stateManager.attach(StartGUI);
    }
    
    public void backToTGStart() {
        stateManager.detach(GameOverGUI);
        showTGStart();
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
        StartGUI.toggle();
        isPaused = false;
        isPauseAllowed = true;
        inGame = true;
        GameState = new GameState();
        GameGUI = new GameGUI(this);
        BombState = new BombState();
        BeamState = new BeamState();
        CreepState = new CreepState();
        TowerState = new TowerState();
        LevelState = new LevelState(debug, levelName);
        GameOverGUI = new GameOverGUI(this);
        attachGameStates();
    }
    
    /**
     * Attaches all of the states necessary for playing a level.
     * Called by startGame().
     */
    private void attachGameStates() {
        stateManager.attach(GameGUI);
        stateManager.attach(GameState);
        stateManager.attach(TowerState);
        stateManager.attach(CreepState);
        stateManager.attach(BeamState);
        stateManager.attach(BombState);
        stateManager.attach(LevelState);
    }
    
    /**
     * Detaches all of the states necessary for playing a level.
     * Called when starting/continuing from the Start menu.
     */
    public void detachGameStates() {
        stateManager.detach(GameGUI);
        stateManager.detach(GameState);
        stateManager.detach(TowerState);
        stateManager.detach(CreepState);
        stateManager.detach(BeamState);
        stateManager.detach(BombState);
        stateManager.detach(LevelState);
    }
    
    
    /**
     * Pauses the game.
     * Called by TGGamePlay
     */
    public void pause() {
        if (isPauseAllowed) {
            if (isPaused) {
                enableStates();
                isPaused = false;
            }
            else {
                disableStates();
                isPaused = true;
            }
        }
    }
        
    
    /**
     * Ends game and detaches the states so they cannot be retrieved.
     * TODO: Integrate with TGGamePlay
     */
    public void gameover() {
        isPauseAllowed = false;
        detachGameStates();
        stateManager.attach(GameOverGUI); 
        inGame = false;
    }
    
    /**
     * These are the states that are disabled on pause.
     */
    public void disableStates() {
        GameState.setEnabled(false);
        LevelState.setEnabled(false);
        BeamState.setEnabled(false);
        BombState.setEnabled(false);
        CreepState.setEnabled(false);
        TowerState.setEnabled(false);
    }
    /** 
     * These are the states that are enabled on unpause.
     */
    public void enableStates() {
        GameState.setEnabled(true);
        CreepState.setEnabled(true);
        LevelState.setEnabled(true);
        BeamState.setEnabled(true);
        BombState.setEnabled(true);
        TowerState.setEnabled(true);
    }
    
    /**
     * This is called from the pause menu to get back to the main menu.  False
     * Alternatively, it is called from the GameOver state, with boolean True
     */
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
    
    public void toggleFrills() {
        if (inGame) {
            GameGUI.toggleFrills();
        }
    }
    
    @Override
    public void destroy() {
        super.destroy();
        //GameState.ex.shutdown();
    }
   

}
