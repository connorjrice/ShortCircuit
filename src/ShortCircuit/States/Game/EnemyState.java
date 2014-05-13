package ShortCircuit.States.Game;

import ShortCircuit.DataStructures.Graph;
import ShortCircuit.Factories.RegCreepFactory;
import ShortCircuit.Factories.GlobFactory;
import ShortCircuit.Factories.RangerFactory;
import ShortCircuit.MapXML.CreepParams;
import ShortCircuit.MapXML.CreepSpawnerParams;
import ShortCircuit.MapXML.TowerParams;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
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
 * TODO: Fix EnemyState random seed for profile
 */
public class EnemyState extends AbstractAppState {

    private Box univ_box = new Box(1, 1, 1);
    private Sphere glob_sphere = new Sphere(32, 32, 1f);
    public Node creepNode = new Node("Creep");
    public Random random;
    private SimpleApplication app;
    private AssetManager assetManager;
    private GameState GameState;
    private FriendlyState FriendlyState;
    private int nextspawner;
    private int nextrandom;
    private float randomCheck = 0;
    private RegCreepFactory cf;
    private GlobFactory gf;
    public ArrayList<Spatial> creepList;
    private ArrayList<Spatial> globList;
    private ArrayList<Spatial> rangerList;
    private ArrayList<Spatial> diggerList;
    private RangerFactory rf;
    private AppStateManager stateManager;
    private GraphicsState GraphicsState;
    private Node rootNode;
    private ArrayList<CreepSpawnerParams> creepSpawnerList;
    private Object[] creepTypes;
    private PathfindingState PathState;
    private HashMap creepParams;

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
        initLists();
    }

    private void initFactories() {
        cf = new RegCreepFactory(GraphicsState, this);
        gf = new GlobFactory(this);
        rf = new RangerFactory(this);
    }

    private void initLists() {
        creepList = new ArrayList<Spatial>();
        globList = new ArrayList<Spatial>();
        random = new Random();
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

    public void setCreepSpawnerList(ArrayList<CreepSpawnerParams> creepSpawnerList) {
        this.creepSpawnerList = creepSpawnerList;
    }

    @Override
    public void update(float tpf) {
        if (isEnabled()) {
            /*if (randomCheck > nextrandom) {
                spawnRandomEnemy();
                getNextRandomSpecialEnemyInt();
                randomCheck = 0;
            } else {
                 randomCheck += tpf;
            }*/
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
        if (!FriendlyState.getTowerList().get(towerVictimIndex).getControl().getIsGlobbed()) {
            Vector3f towerVictimLocation = FriendlyState.getTowerList().get(towerVictimIndex).getTowerVec();
            Spatial glob = gf.getGlob(towerVictimLocation, towerVictimIndex);
            FriendlyState.getTowerList().get(towerVictimIndex).getControl().globTower();
            creepNode.attachChild(glob);
            globList.add(glob);
        } else {
            spawnGlob();
        }
    }

    public Sphere getGlobSphere() {
        return glob_sphere;
    }

    private void spawnRanger() {
        int towerVictimIndex = random.nextInt(FriendlyState.getTowerList().size());
        Spatial ranger = rf.getRanger(getRangerSpawnPoint(towerVictimIndex),
                towerVictimIndex);
        rangerList.add(ranger);
        creepNode.attachChild(ranger);
    }

    public Geometry getRangerGeom() {
        return new Geometry("Ranger", new Box(1, 1, 1));
    }

    /**
     * TODO: Algorithm for determining where rangers should spawn.
     *
     * @param towerVictimIndex
     * @return
     */
    private Vector3f getRangerSpawnPoint(int towerVictimIndex) {
        return new Vector3f();
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

    public ArrayList<CreepSpawnerParams> getCreepSpawnerList() {
        return creepSpawnerList;
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

    public int getCreepListSize() {
        return creepList.size();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public ArrayList<TowerParams> getTowerList() {
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
