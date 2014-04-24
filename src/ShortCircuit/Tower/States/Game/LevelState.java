package ShortCircuit.Tower.States.Game;

import ShortCircuit.GUI.StartGUI;
import ShortCircuit.Tower.Cheats.CheatState;
import ShortCircuit.Tower.MapXML.MapGenerator;
import ShortCircuit.Tower.States.GUI.GameGUI;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * LevelState calls the appropriate methods in its sibling states to create a 
 * level. A level is defined at this moment in time as a floor, at least one 
 * base vector, n number of towers, and n number of creep spawners.
 * @author Connor Rice
 */
public class LevelState extends AbstractAppState {
    private SimpleApplication app;
    private GameState GameState;
    public Node rootNode;
    private TowerState TowerState;
    private CreepState CreepState;
    private MapGenerator mg;
    private boolean isDebug;
    protected boolean gameOver = false;
    private final String levelName;
    private FilterState FilterState;
    
    public LevelState(boolean _isDebug, String _levelName) {
        isDebug = _isDebug;
        levelName = _levelName;
    }
    
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.FilterState = this.app.getStateManager().getState(FilterState.class);
        this.GameState = this.app.getStateManager().getState(GameState.class);
        this.TowerState = this.app.getStateManager().getState(TowerState.class);
        this.CreepState = this.app.getStateManager().getState(CreepState.class);
        this.rootNode = this.app.getRootNode();
        begin();
    }
    
    public void begin() {
        if (!isDebug) {
            newGame(levelName);
        }
        else {
            debugGame();
        }
    }
    
    
    /**
     * Initializes the XML Parser, builds the map in GameState with specified
     * settings.
     */
    public void newGame(String levelname) {
        initMG(levelname);
        FilterState.initFilters(mg.getFilterParams());
        GameState.setLevelParams(mg.getLevelParams());
        GameState.createLight();
        GameState.createFloor(mg.getFloorScale(), getFloorMatLoc());
        GameState.createBase("/Base", mg.getBaseVec(), mg.getBaseScale());
        TowerState.buildUnbuiltTowers(mg.getUnbuiltTowerVecs());
        TowerState.buildStarterTowers(mg.getStarterTowers());
        TowerState.attachTowerNode();
        CreepState.buildCreepSpawners(mg.getCreepSpawnVecs(), mg.getCreepSpawnDirs());
        CreepState.setBaseBounds();
        CreepState.initMaterials();
        CreepState.attachCreepNode();
        GameState.attachWorldNode();
        app.getStateManager().getState(StartGUI.class).hideloading();
    }
    
    
    public void debugGame() {
        initMG(levelName);
        FilterState.initFilters(mg.getFilterParams());
        GameState.createLight();
        GameState.setLevelParams(mg.getLevelParams());
        GameState.createFloor(mg.getFloorScale(), getFloorMatLoc());
        GameState.createBase("/Base", mg.getBaseVec(), mg.getBaseScale());
        TowerState.buildUnbuiltTowers(mg.getUnbuiltTowerVecs());
        TowerState.buildStarterTowers(mg.getStarterTowers());
        TowerState.attachTowerNode();
        CreepState.buildCreepSpawners(mg.getCreepSpawnVecs(), mg.getCreepSpawnDirs());
        CreepState.attachCreepNode();
        CreepState.setBaseBounds();
        CreepState.initMaterials();
        GameState.attachWorldNode();
        CheatState cHS = app.getStateManager().getState(CheatState.class);
        cHS.makeTowersBadAss();
        app.getStateManager().getState(StartGUI.class).hideloading();
    }
    
    /**
     * Internal method. Initializes MapGenerator and parses a file given as
     * a string as a parameter
     * @param levelname - name of XML file to be loaded from:
     * assets.XML
     */
    private void initMG(String levelname) {
        mg = new MapGenerator(levelname, app);
        mg.parseXML();
    }
    
    
    public String getFloorMatLoc() {
        return "Materials/"+GameState.getMatDir()+"/Floor.j3m";
    }
    
    public MapGenerator getMG() {
        return mg;
    }
    
    public Vector3f getBaseVec() {
        return mg.getBaseVec();
    }
    
    public Vector3f getBaseScale() {
        return mg.getBaseScale();
    }
    
    public Vector3f getFloorScale() {
        return mg.getFloorScale();
    }
    
    public ArrayList<Vector3f> getCreepSpawnerVecs() {
        return mg.getCreepSpawnVecs();
    }
    
    public ArrayList<Vector3f> getUnbuiltTowerVecs() {
        return mg.getUnbuiltTowerVecs();
    }
   
    @Override
    public void cleanup() {
        super.cleanup();
        rootNode.detachAllChildren();
    }

}