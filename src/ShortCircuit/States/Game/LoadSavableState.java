package ShortCircuit.States.Game;

import ShortCircuit.MapXML.MapGenerator;
import ShortCircuit.Controls.CreepSpawnerControl;
import ShortCircuit.Controls.TowerControl;
import ShortCircuit.States.GUI.StartGUI;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 * LoadingState calls the appropriate methods in its sibling states to create a 
 * level. A level is defined at this moment in time as a floor, at least one 
 * base vector, n number of towers, and n number of creep spawners.
 * @author Connor Rice
 */
public class LoadSavableState extends AbstractAppState {
    private SimpleApplication app;
    private GameState GameState;
    private EnemyState EnemyState;
    private MapGenerator mg;
    private String levelName;
    private AppStateManager stateManager;
    private StartGUI StartGUI;
    private GraphicsState GraphicsState;
    private Node rootNode;
    private AssetManager assetManager;
    
    
    private ArrayList<Spatial> towerList;
    private ArrayList<Spatial> spawnerList;
    private FriendlyState FriendlyState;
    
    public LoadSavableState() {}
    
    public LoadSavableState(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        getStates();
        initLists();
        loadWorld();
        visitScene();
        setParams();
    }
    
    private void getStates() {
        this.GraphicsState = this.stateManager.getState(GraphicsState.class);
        this.GameState = this.stateManager.getState(GameState.class);
        this.EnemyState = this.stateManager.getState(EnemyState.class);
        this.FriendlyState = this.stateManager.getState(FriendlyState.class);
        this.StartGUI = this.stateManager.getState(StartGUI.class);
    }
    
    private void initLists() {
        towerList = new ArrayList<Spatial>();
        spawnerList = new ArrayList<Spatial>();
    }
    
    private void loadWorld() {
        Node loadedWorld = (Node) assetManager.loadModel("Models/"+levelName+".j3o");
        loadedWorld.setName("LoadedWorld");
        rootNode.attachChild(loadedWorld);
    }
    
    private void visitScene() {
        SceneGraphVisitor vis = new SceneGraphVisitor() {
            public void visit(Spatial spatial) {
                if (spatial.getName().equals("Base")) {
                    GameState.setBaseBounds(spatial.getWorldBound());
                    GameState.setFormattedBaseCoords(spatial);
                } else if (spatial.getName().equals("Tower")) {
                    addTower(spatial);
                } else if (spatial.getName().equals("Spawner")) {
                    spawnerList.add(spatial);
                    spatial.addControl(new CreepSpawnerControl(EnemyState));
                }
            }
        };
        rootNode.breadthFirstTraversal(vis);
    }
    
    private void addTower(Spatial spatial) {
        TowerControl tc = new TowerControl(FriendlyState, spatial.getLocalTranslation());
        spatial.addControl(tc);
        if (spatial.getUserData("Type").equals("Tower1")) {
            tc.addCharges();
        }
        tc.setBeamWidth();
        towerList.add(spatial);

    }
    
    private void setParams() {
        initMG(levelName, app);
        GraphicsState.setGraphicsParams(mg.getGraphicsParams());
        EnemyState.setEnemyParams(mg.getGraphicsParams().getCreepMap());
        EnemyState.setCreepSpawnerList(spawnerList);
        GameState.setGPBuild(mg.getGameplayParams());
        FriendlyState.setTowerList(towerList);
    }


    private void initMG(String levelname, SimpleApplication app) {
        mg = new MapGenerator(levelname, app);
    }
    
    private void updateStartGUI() {
        StartGUI.hideloading();
        StartGUI.updateAtlas(GameState.getAtlas());
    }

   
    @Override
    public void cleanup() {
        super.cleanup();
    }

}