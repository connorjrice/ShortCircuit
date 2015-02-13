package sc.states.game;

import sc.controls.CreepSpawnerControl;
import sc.controls.TowerControl;
import sc.objects.GameplayParams;
import sc.objects.GraphicsParams;
import sc.states.gui.StartGUI;
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
 *
 * @author Connor Rice
 */
public class LoadSavableState extends AbstractAppState {

    private SimpleApplication app;
    private GameState GameState;
    private EnemyState EnemyState;
    private String levelName;
    private AppStateManager stateManager;
    private StartGUI StartGUI;
    private GraphicsState GraphicsState;
    private Node rootNode;
    private AssetManager assetManager;
    private ArrayList<Spatial> towerList;
    private ArrayList<Spatial> spawnerList;
    private FriendlyState FriendlyState;
    private GameplayParams gap;
    private GraphicsParams grp;
    private TutorialState TutorialState;

    public LoadSavableState() {
    }

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
        Node loadedWorld = (Node) assetManager.loadModel("Models/" +
                levelName + ".j3o");
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
        setParams();
    }

    private void addTower(Spatial spatial) {
        TowerControl tc = new TowerControl(FriendlyState,
                spatial.getLocalTranslation());
        spatial.addControl(tc);
        if (spatial.getUserData("Type").equals("Tower1")) {
            tc.addCharges();
        }
        tc.setBeamWidth();
        towerList.add(spatial);

    }

    private void setParams() {
        grp = (GraphicsParams) rootNode.getChild("LoadedWorld")
                .getUserData("GraphicsParams");
        gap = (GameplayParams) rootNode.getChild("LoadedWorld")
                .getUserData("GameplayParams");
        grp.parseCreeps();
        GraphicsState.setGraphicsParams(grp);

        EnemyState.setEnemyParams(grp.getCreepMap());
        EnemyState.setCreepSpawnerList(spawnerList);
        EnemyState.initLists(gap.getLevelParams().getProfile());
        if (getProfile()) {
            ProfileState ps = new ProfileState();
            stateManager.attach(ps);
        } else {
            updateStartGUI();
        }
        GameState.setGPBuild(gap);
        FriendlyState.setTowerList(towerList);
        if (getTutorial()) {
            TutorialState = new TutorialState();
            stateManager.attach(TutorialState);
        }
    }

    public boolean getProfile() {
        return gap.getLevelParams().getProfile();
    }

    public boolean getTutorial() {
        return gap.getLevelParams().getTutorial();
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