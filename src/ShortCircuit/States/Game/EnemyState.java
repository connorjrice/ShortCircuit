package ShortCircuit.States.Game;

import ShortCircuit.Controls.TowerControl;
import DataStructures.Graph;
import ShortCircuit.Factories.RegCreepFactory;
import ShortCircuit.Factories.GlobFactory;
import ScSDK.MapXML.CreepParams;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author Connor Rice
 */
public class EnemyState extends AbstractAppState {

    private SimpleApplication app;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private GameState GameState;
    private FriendlyState FriendlyState;
    private GraphicsState GraphicsState;
    private PathfindingState PathState;
    private Node rootNode;
    private Node creepNode = new Node("Creep");
    private Box univ_box = new Box(1, 1, 1);
    private Sphere glob_sphere = new Sphere(32, 32, 1f);
    private Random random;
    private ArrayList<Spatial> creepList;
    private ArrayList<Spatial> globList;
    private ArrayList<Spatial> creepSpawnerList;
    private HashMap creepParams;
    private Object[] creepTypes;
    private RegCreepFactory cf;
    private GlobFactory gf;
    private float randomCheck = 0;
    private int nextspawner;
    private int nextrandom;

    public EnemyState() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.stateManager = this.app.getStateManager();
        this.assetManager = this.app.getAssetManager();
        this.GameState = this.stateManager.getState(GameState.class);
        this.FriendlyState = this.stateManager.getState(FriendlyState.class);
        this.GraphicsState = this.stateManager.getState(GraphicsState.class);
        this.PathState = this.stateManager.getState(PathfindingState.class);
        this.rootNode = this.app.getRootNode();
        initFactories();
    }

    private void initFactories() {
        cf = new RegCreepFactory(GraphicsState, this);
        gf = new GlobFactory(this);
        //rf = new RangerFactory(this);
    }

    public void initLists(boolean isProfile) {
        creepList = new ArrayList<Spatial>();
        globList = new ArrayList<Spatial>();
        if (isProfile) {
            random = new Random(42);
        } else {
            random = new Random();
        }
        nextrandom = random.nextInt(50);
    }

    public void setEnemyParams(HashMap creepParams) {
        this.creepTypes = creepParams.keySet().toArray();
        this.creepParams = creepParams;
        attachCreepNode();
    }

    public String[] getCreepTypes() {
        return (String[]) creepTypes;
    }

    public void setCreepSpawnerList(ArrayList<Spatial> creepSpawnerList) {
        this.creepSpawnerList = creepSpawnerList;
    }

    @Override
    public void update(float tpf) {
        if (isEnabled()) {
            if (randomCheck > nextrandom) {
                spawnRandomEnemy();
                getNextRandomSpecialEnemyInt();
                randomCheck = 0;
            } else {
                randomCheck += tpf;
            }
        }

    }

    /**
     * Determines how many seconds have to pass before another random enemy (non
     * standard creep) is spawned. TODO: Implement better system for random
     * enemies, perhaps XML.
     */
    private void getNextRandomSpecialEnemyInt() {
        nextrandom = random.nextInt(50);
    }

    private void spawnRandomEnemy() {
        spawnGlob();
    }

    /**
     * Spawns a glob. Checks to see if there is already a glob on the tower that
     * was randomly selected, and if so, calls it's self again to find a new
     * tower.
     */
    private void spawnGlob() {
        int towerVictimIndex = random.nextInt(FriendlyState.getTowerList().size());
        if (!FriendlyState.getTowerList().get(towerVictimIndex)
                .getControl(TowerControl.class).getIsGlobbed()) {
            Vector3f towerVictimLocation = FriendlyState
                    .getTowerList().get(towerVictimIndex).getLocalTranslation();
            Spatial glob = gf.getGlob(towerVictimLocation, towerVictimIndex);
            FriendlyState.getTowerList().get(towerVictimIndex)
                    .getControl(TowerControl.class).globTower();
            creepNode.attachChild(glob);
            globList.add(glob);
        } else {
            spawnGlob();
        }
    }

    public Sphere getGlobSphere() {
        return glob_sphere;
    }

    public void attachCreepNode() {
        rootNode.attachChild(creepNode);
    }

    public void spawnRegCreep(Vector3f spawnpoint) {
        creepList.add(cf.getCreep(spawnpoint, getNextCreepParams()));
        creepNode.attachChild(creepList.get(creepList.size() - 1));
    }

    public CreepParams getNextCreepParams() {
        return (CreepParams) creepParams.get(getNextCreepType());
    }

    public String getNextCreepType() {
        return (String) creepTypes[random.nextInt(creepTypes.length)];
    }

    public String getMatDir() {
        return GraphicsState.getMatDir();
    }

    public String getCreepMatLoc(String type) {
        return "Materials/" + getMatDir() + "/" + type + "Creep.j3m";
    }

    /**
     * Determines how many creeps should be on the map based upon the player's
     * level. The numbers are determined by nodes in the level XML file.
     *
     */
    public int getNumCreepsByLevel() {
        return GameState.getNumCreeps();
    }

    public ArrayList<Spatial> getCreepSpawnerList() {
        return creepSpawnerList;
    }

    public Vector3f getBaseVec() {
        return GameState.getBaseVec();
    }

    public BoundingVolume getBaseBounds() {
        return GameState.getBaseBounds();
    }

    public Node getCreepNode() {
        return creepNode;
    }

    public ArrayList<Spatial> getCreepList() {
        return creepList;
    }

    public Spatial getCreep(int i) {
        return creepList.get(i);
    }

    public int getCreepListSize() {
        return creepList.size();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public ArrayList<Spatial> getTowerList() {
        return FriendlyState.getTowerList();
    }

    public boolean[] getGlobbedTowerList() {
        return FriendlyState.getGlobbedTowerList();
    }

    public ArrayList<Spatial> getGlobList() {
        return globList;
    }

    public Box getUnivBox() {
        return univ_box;
    }

    public void decPlrHealth(int dam) {
        GameState.decPlrHealth(dam);
    }

    public void incPlrBudget(int value) {
        GameState.incPlrBudget(value);
    }

    public void incPlrScore(int inc) {
        GameState.incPlrScore(inc);
    }

    public void goToNextSpawner() {
        if (creepSpawnerList.size() > 1) {
            if (nextspawner < creepSpawnerList.size() - 1) {

                nextspawner += 1;
            } else {
                nextspawner = 0;
            }
        } else {
            nextspawner = 0;
        }
    }

    public int getNextSpawner() {
        return nextspawner;
    }

    public ScheduledThreadPoolExecutor getEx() {
        return GameState.getEx();
    }

    public SimpleApplication getApp() {
        return app;
    }

    public void seedForProfile() {
        random = new Random(42);
        nextrandom = random.nextInt(10);
        randomCheck = random.nextInt(10);
    }

    public Graph getWorldGraph() {
        return PathState.getWorldGraph();
    }

    public String getFormattedBaseCoords() {
        return GameState.getFormattedBaseCoords();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        creepNode.detachAllChildren();
        globList.clear();
        creepList.clear();
    }
}
