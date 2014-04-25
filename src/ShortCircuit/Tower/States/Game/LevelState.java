package ShortCircuit.Tower.States.Game;

import ShortCircuit.GUI.StartGUI;
import ShortCircuit.Tower.Cheats.CheatState;
import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.MapXML.MapGenerator;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;

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
    private boolean isProfile;
    protected boolean gameOver = false;
    private final String levelName;
    private FilterState FilterState;
    private AppStateManager stateManager;
    private float profileUpgradeTimer = 0f;
    private double profileBombTimer;
    private HelperState HelperState;
    private double profileEmptyTimer;
    private double profileChargerTimer;
    
    public LevelState(boolean _isProfile, String _levelName) {
        isProfile = _isProfile;
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
        this.HelperState = this.app.getStateManager().getState(HelperState.class);
        this.stateManager = stateManager;
        this.rootNode = this.app.getRootNode();
        begin();
    }
    
    public void begin() {
        if (!isProfile) {
            newGame(levelName);
        }
        else {
            profileGame();
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
        tutorial(mg.getLevelParams().getTutorial());
    }
    
    public void tutorial(boolean tutorial) {
        if (tutorial) {
            TutorialState ts = new TutorialState();
            stateManager.attach(ts);
        }
    }
    
    
    public void profileGame() {
        initMG(levelName);
        FilterState.initFilters(mg.getFilterParams());
        GameState.createLight();
        GameState.setLevelParams(mg.getLevelParams());
        GameState.createFloor(mg.getFloorScale(), getFloorMatLoc());
        GameState.createBase("/Base", mg.getBaseVec(), mg.getBaseScale());
        TowerState.buildUnbuiltTowers(mg.getUnbuiltTowerVecs());
        TowerState.buildStarterTowers(mg.getStarterTowers());
        TowerState.attachTowerNode();
        CreepState.seedForProfile();
        CreepState.buildCreepSpawners(mg.getCreepSpawnVecs(), mg.getCreepSpawnDirs());
        CreepState.attachCreepNode();
        CreepState.setBaseBounds();
        CreepState.initMaterials();
        GameState.attachWorldNode();
    }
    
    @Override
    public void update(float tpf) {
        if (isProfile) {
            profileUpgradeTowers(tpf);
            profileDropBombs(tpf);
            profileEmptyTowers(tpf);
            profileBuildCharger(tpf);
        }
    }
    
    private void profileUpgradeTowers(float tpf) {
        if (profileUpgradeTimer > .75) {
            for (int i = 0; i < TowerState.getTowerList().size(); i++) { 
                TowerState.selectedTower = i;
                TowerState.upgradeTower();
            }
            profileUpgradeTimer = 0;
        }
        else {
            profileUpgradeTimer += tpf;
        }
    }
    
    private void profileDropBombs(float tpf) {
        if (profileBombTimer > .05) {
            GameState.dropBomb(CreepState.getCreepList().get(0).getLocalTranslation(), .002f);
            profileBombTimer = 0;
        }
        else {
            profileBombTimer += tpf;
        }
    }
    
    private void profileEmptyTowers(float tpf) {
        if (profileEmptyTimer > .3) {
            for (int i = 0; i < TowerState.getTowerList().size(); i++) {
                TowerState.getTowerList().get(i).getControl(TowerControl.class).charges.clear();
            }
            profileEmptyTimer = 0;
        }
        else {
            profileEmptyTimer += tpf;
        }
    }
    
    private void profileBuildCharger(float tpf) {
        if (profileChargerTimer > .8) {
            HelperState.createCharger();
            profileChargerTimer = 0;
        }
        else {
            profileChargerTimer += tpf;
        }
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