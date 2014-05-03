package ShortCircuit.Tower.States.Game;

import ShortCircuit.Tower.Factories.STDCreepFactory;
import ShortCircuit.Tower.Objects.Game.CreepTraits;
import ShortCircuit.Tower.Factories.STDCreepSpawnerFactory;
import ShortCircuit.Tower.Factories.GlobFactory;
import ShortCircuit.Tower.Factories.RangerFactory;
import ShortCircuit.Tower.MapXML.Objects.CreepSpawnerParams;
import ShortCircuit.Tower.MapXML.Objects.TowerParams;
import ShortCircuit.Tower.Objects.Loading.EnemyParams;
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
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * TODO: Make spawning/direction/bounds more universal
 * @author Connor Rice
 */
public class EnemyState extends AbstractAppState {

    private Box univ_box = new Box(1, 1, 1);
    private Sphere glob_sphere = new Sphere(32, 32, 1f);
    public Node creepNode = new Node("Creep");
    private static final Vector3f SM_CREEP_SIZE =
            new Vector3f(0.125f, 0.125f, 0.20f);
    private static final Vector3f MD_CREEP_SIZE =
            new Vector3f(0.25f, 0.25f, 0.40f);
    private static final Vector3f LG_CREEP_SIZE =
            new Vector3f(0.4f, 0.4f, 0.60f);
    private static final Vector3f XL_CREEP_SIZE =
            new Vector3f(0.6f, 0.6f, 0.8f);
    private static final int SM_CREEP_HEALTH = 100;
    private static final int MD_CREEP_HEALTH = 200;
    private static final int LG_CREEP_HEALTH = 400;
    private static final int XL_CREEP_HEALTH = 800;
    private static final float SM_CREEP_SPEED = 0.000085f;
    private static final float MD_CREEP_SPEED = 0.000035f;
    private static final float LG_CREEP_SPEED = 0.000025f;
    private static final float XL_CREEP_SPEED = 0.000030f;

    public Random random = new Random();
    private SimpleApplication app;
    private AssetManager assetManager;
    private GameState GameState;
    private FriendlyState FriendlyState;
    private int nextspawner;
    private int nextrandom = random.nextInt(50);
    private float randomCheck = 0;
    private Node worldNode;
    private STDCreepFactory cf;
    private GlobFactory gf;

    private BoundingVolume basebounds;
    public ArrayList<Spatial> creepList;
    private ArrayList<Spatial> globList;
    private ArrayList<Spatial> rangerList;
    private ArrayList<Spatial> diggerList;
    private RangerFactory rf;
    private EnemyParams ep;
    private AppStateManager stateManager;
    private GraphicsState GraphicsState;
    private Node rootNode;
    private ArrayList<CreepSpawnerParams> creepSpawnerList;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.stateManager = this.app.getStateManager();
        this.assetManager = this.app.getAssetManager();
        this.GameState = this.stateManager.getState(GameState.class);
        this.FriendlyState = this.stateManager.getState(FriendlyState.class);
        this.GraphicsState = this.stateManager.getState(GraphicsState.class);
        this.worldNode = this.GameState.getWorldNode();
        this.rootNode = this.app.getRootNode();
        initFactories();
        initLists();
    }

    private void initFactories() {
        cf = new STDCreepFactory(this);
        gf = new GlobFactory(this);

        rf = new RangerFactory(this);
    }

    private void initLists() {
        creepList = new ArrayList<Spatial>();
        globList = new ArrayList<Spatial>();
    }
    
    public void initAssets() {

    }
    
    public void setEnemyParams(EnemyParams ep) {
        this.ep = ep;
        attachCreepNode();
    }
    
    public void setCreepSpawnerList(ArrayList<CreepSpawnerParams> creepSpawnerList) {
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
        if (FriendlyState.getGlobbedTowerList().size() < FriendlyState.getTowerList().size()) {
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
        return new Geometry("Ranger", new Box(1,1,1));
    }
    
    /**
     * TODO: Algorithm for determining where rangers should spawn.
     * @param towerVictimIndex
     * @return 
     */
    private Vector3f getRangerSpawnPoint(int towerVictimIndex) {
        
        return new Vector3f();
    }

    public void attachCreepNode() {
        rootNode.attachChild(creepNode);
    }



    /**
     * This is where the process of building a standard creep begins. The flow
     * of creep is: 1. startSTDCreep, which is called by a CreepSpawner. The
     * true/false boolean determines whether or not the creep is moving
     * vertically (along the Y-axis) or horizontally (along the X-axis). The
     * random numbers are there to determine the type (sm, md, etc).
     *
     * 2. Into prepare Sm/Md STD Creep() prepare(type)(standard)creep 2a. At
     * this state, the CreepTraits object is created.
     *
     * 3. Into createSTDCreep, which takes care of spawning the creep.
     */
    public void startSTDCreep(Vector3f spawnerVec, int spawnIndex) {
        int size = random.nextInt(15);
        if (spawnerVec.getY() == 0) {
            if (size < 8) {
                prepareSmSTDCreep(spawnerVec, spawnIndex, true);
            } else if (8 <= size && size < 11) {
                prepareMdSTDCreep(spawnerVec, spawnIndex, true);
            } else if (size >= 11 && size <= 13) {
                prepareLgSTDCreep(spawnerVec, spawnIndex, true);
            } else if (size == 14) {
                prepareXlSTDCreep(spawnerVec, spawnIndex, true);
            }

        } else {
            if (size < 8) {
                prepareSmSTDCreep(spawnerVec, spawnIndex, false);
            } else if (8 <= size && size < 11) {
                prepareMdSTDCreep(spawnerVec, spawnIndex, false);
            } else if (size >= 11 && size <= 13) {
                prepareLgSTDCreep(spawnerVec, spawnIndex, false);
            } else if (size == 14) {
                prepareXlSTDCreep(spawnerVec, spawnIndex, false);
            }

        }
    }

    private void prepareSmSTDCreep(Vector3f spawnervec, int spawnIndex, boolean vertical) {
        if (vertical) {
            createSTDCreep(new CreepTraits("Small", SM_CREEP_HEALTH, spawnIndex, getCreepVecVert(spawnervec),
                    SM_CREEP_SIZE, SM_CREEP_SPEED, 1, getCreepMatLoc("Small")));
        } else {
            createSTDCreep(new CreepTraits("Small", SM_CREEP_HEALTH, spawnIndex, getCreepVecHoriz(spawnervec),
                    SM_CREEP_SIZE, SM_CREEP_SPEED, 1, getCreepMatLoc("Small")));
        }
    }

    private void prepareMdSTDCreep(Vector3f spawnervec, int spawnIndex, boolean vertical) {
        if (vertical) {
            createSTDCreep(new CreepTraits("Medium", MD_CREEP_HEALTH, spawnIndex, getCreepVecVert(spawnervec),
                    MD_CREEP_SIZE, MD_CREEP_SPEED, 2, getCreepMatLoc("Medium")));
        } else {
            createSTDCreep(new CreepTraits("Medium", MD_CREEP_HEALTH, spawnIndex, getCreepVecHoriz(spawnervec),
                    MD_CREEP_SIZE, MD_CREEP_SPEED, 2, getCreepMatLoc("Medium")));
        }
    }

    private void prepareLgSTDCreep(Vector3f spawnervec, int spawnIndex, boolean vertical) {
        if (vertical) {
            createSTDCreep(new CreepTraits("Large", LG_CREEP_HEALTH, spawnIndex, getCreepVecVert(spawnervec),
                    LG_CREEP_SIZE, LG_CREEP_SPEED, 5, getCreepMatLoc("Large")));
        } else {
            createSTDCreep(new CreepTraits("Large", LG_CREEP_HEALTH, spawnIndex, getCreepVecHoriz(spawnervec),
                    LG_CREEP_SIZE, LG_CREEP_SPEED, 5, getCreepMatLoc("Large")));
        }
    }

    private void prepareXlSTDCreep(Vector3f spawnervec, int spawnIndex, boolean vertical) {
        if (vertical) {
            createSTDCreep(new CreepTraits("Giant", XL_CREEP_HEALTH, spawnIndex, getCreepVecVert(spawnervec),
                    XL_CREEP_SIZE, XL_CREEP_SPEED, 10, getCreepMatLoc("Giant")));
        } else {
            createSTDCreep(new CreepTraits("Giant", LG_CREEP_HEALTH, spawnIndex, getCreepVecHoriz(spawnervec),
                    XL_CREEP_SIZE, XL_CREEP_SPEED, 10, getCreepMatLoc("Giant")));
        }
    }

    /**
     * Creates a creep based upon the input from creepBuilder.
     *
     * @param ct, object containing traits of the creep
     */
    private void createSTDCreep(CreepTraits ct) {
        creepList.add(cf.getCreep(ct));
        creepNode.attachChild(creepList.get(creepList.size() - 1));
    }


    public String getMatDir() {
        return GraphicsState.getMatDir();
    }

    private Vector3f getCreepVecVert(Vector3f spawnervec) {
        return new Vector3f(spawnervec.getX(), getCreepNextBound(), 0.2f);
    }

    private Vector3f getCreepVecHoriz(Vector3f spawnervec) {
        return new Vector3f(getCreepNextBound(), spawnervec.getY(), 0.2f);
    }

    /**
     * Provides some different spawn points for the creep along the correct
     * axis.
     *
     * @return an offset for the creep's spawning point
     */
    private float getCreepNextBound() {
        int n = random.nextInt(2);
        if (n == 0) {
            return random.nextFloat() * 1.2f;
        } else {
            return -random.nextFloat() * 1.2f;
        }
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
        return basebounds;
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

    public ArrayList<Integer> getGlobbedTowerList() {
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
        }
        else {
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

    public void setBaseBounds() {
        basebounds = worldNode.getChild("Base").getWorldBound();
    }

    public void seedForProfile() {
        random = new Random(42);
    }
    


    @Override
    public void cleanup() {
        super.cleanup();
        creepNode.detachAllChildren();
        globList.clear();
        creepList.clear();
    }
}
