package ShortCircuit;

import ShortCircuit.Tower.Cheats.CheatGUI;
import ShortCircuit.Tower.Cheats.CheatState;
import ShortCircuit.Tower.States.Game.GameState;
import ShortCircuit.Tower.States.Game.BeamState;
import ShortCircuit.Tower.States.Game.CreepState;
import ShortCircuit.Tower.States.Game.TowerState;
import ShortCircuit.Tower.States.GUI.GameGUI;
import ShortCircuit.Tower.States.GUI.GameOverGUI;
import ShortCircuit.GUI.StartGUI;
import ShortCircuit.Tower.States.Game.LevelState;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.BloomFilter.GlowMode;
import com.jme3.system.AppSettings;
import java.util.concurrent.Callable;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ShortCircuit: A tower defense game
 *
 * @author Connor Rice
 */
public class TowerDefenseMain extends SimpleApplication {

    private GameState GameState;
    private BeamState BeamState;
    private CreepState CreepState;
    private TowerState TowerState;
    private LevelState LevelState;
    private CheatState CheatState;
    public StartGUI StartGUI;
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
    private FilterPostProcessor fpp;
    private AudioNode theme;
    private final static String MAPPING_ACTIVATE = "Touch";
    private BloomFilter bloom;

    /**
     * Main Method The Big Cheese
     *
     * @param args
     */
    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.OFF);
        TowerDefenseMain app = new TowerDefenseMain();
        AppSettings sets = new AppSettings(true);
        sets.setSettingsDialogImage("Interface/loading.png");
        sets.setFrameRate(60);
        app.setSettings(sets);
        app.start();
    }
    private AudioNode endTheme;



    /**
     * Initialize camera, add input mapping, bring up start menu. Also adjust
     * statview/fps
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
        
        initFilters();
        showTGStart();
        underPinning();

    }
    /**
     * Sets up the FilterPostProcessor and Bloom filter used by the game.
     * Called upon initialization of the game.
     */
    private void initFilters() {
        fpp = new FilterPostProcessor(assetManager);
        bloom = new BloomFilter(GlowMode.SceneAndObjects);
        bloom.setDownSamplingFactor(2.0f);
        bloom.setBlurScale(4.0f);
        bloom.setExposurePower(7f);
        fpp.addFilter(bloom);
        viewPort.addProcessor(fpp);
    }

    /**
     * Initializes the theme that plays throughout the game.
     * Sets volume, sets looping true, and begins playing.
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
     * Toggle the bloom filter.
     */
    public void toggleBloom() {
        if (viewPort.getProcessors().contains(fpp)) {
            viewPort.removeProcessor(fpp);
        } else {
            viewPort.addProcessor(fpp);
        }

    }

    /**
     * Method for changing the bloom filter's exposure power.
     * @param newVal - what it will be changed to
     */
    public void setBloomExposurePower(float newVal) {
        bloom.setExposurePower(newVal);
    }

    /**
     * Method for changing the bloom filter's blur scape.
     * @param newVal - what it will be changed to
     */
    public void setBloomBlurScape(float newVal) {
        bloom.setBlurScale(newVal);
    }

    /**
     * Method fo setting the bloom filter's intensity
     * @param newVal - what it should be changed to.
     */
    public void setBloomIntensity(float newVal) {
        bloom.setBloomIntensity(newVal);
    }

    /**
     * Increments the bloom filter's intensity
     * @param add - value to be added to the filter.
     */
    public void incBloomIntensity(float add) {
        bloom.setBloomIntensity(bloom.getBloomIntensity() + add);
    }

    /**
     * Changes global volume.
     * @param volume -New volume level.
     * Not implemented yet.
     */
    public void setVolume(int volume) {
    }

    public void showTGStart() {
        StartGUI = new StartGUI(this);
        stateManager.attach(StartGUI);

    }

    public void backToTGStart() {
        stateManager.detach(GameOverGUI);
        stateManager.attach(StartGUI);
        StartGUI.toggle();
    }
    
    public void toggleCheatsWindow() {
        CheatGUI.toggleCheatWindow();
    }
    
    /**
     * Instantiates all Game States, detaches Start Menu. Then, starts the game
     * by attaching all of the states.
     *
     * @param debug - different method calls need to happen for debug games.
     * @param levelName - Name of the level to be loaded by LevelState The
     * levelName is given by the StartMenu state. The levelName corresponds to
     * an XML file located in: assets/XML; the file extension is .lvl.xml
     */
    public void startGame(boolean debug, String levelName) {
        this.enqueue(new Callable() {
            public Object call() throws InterruptedException {

                //guiViewPort.attachScene(StartGUI.getInd());
                //rootNode.updateGeometricState();
                //guiNode.updateGeometricState();
                return null;
            }
        });

        StartGUI.toggle();
        isPaused = false;
        isPauseAllowed = true;
        inGame = true;
        CheatState = new CheatState();
        CheatGUI = new CheatGUI();
        GameState = new GameState();
        LevelState = new LevelState(debug, levelName);
        GameGUI = new GameGUI(this);
        BeamState = new BeamState();
        CreepState = new CreepState();
        TowerState = new TowerState();
        GameOverGUI = new GameOverGUI(this);
        attachGameStates();
    }

    /**
     * Attaches all of the states necessary for playing a level. Called by
     * startGame().
     */
    private void attachGameStates() {
        stateManager.attach(GameGUI);
        stateManager.attach(GameState);
        stateManager.attach(TowerState);
        stateManager.attach(CreepState);
        stateManager.attach(BeamState);
        stateManager.attach(LevelState);
        stateManager.attach(CheatState);
        stateManager.attach(CheatGUI);
    }

    /**
     * Detaches all of the states necessary for playing a level. Called when
     * starting/continuing from the Start menu.
     */
    public void detachGameStates() {
        stateManager.detach(GameGUI);
        stateManager.detach(GameState);
        stateManager.detach(TowerState);
        stateManager.detach(CreepState);
        stateManager.detach(BeamState);
        stateManager.detach(LevelState);
        stateManager.detach(CheatState);
        stateManager.detach(CheatGUI);
    }

    /**
     * Pauses the game. Called by TGGamePlay
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
     * Ends game and detaches the states so they cannot be retrieved. TODO:
     * Integrate with TGGamePlay
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
    public void disableTowerGameStates() {
        GameState.setEnabled(false);
        LevelState.setEnabled(false);
        BeamState.setEnabled(false);
        CreepState.setEnabled(false);
        TowerState.setEnabled(false);
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
    }

    public boolean isStartWindowShown() {
        return StartGUI.mainWindowShown();
    }

    /* This is called from the pause menu to get back to the main menu.  False
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

    public boolean isPaused() {
        return isPaused;
    }

    public void toggleFrills() {
        if (inGame) {
            GameGUI.toggleFrills();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        theme.stop();
        rootNode.detachAllChildren();
        //GameState.ex.shutdown();
    }
}
